package com.baomidou.mybatisplus.core.metadata;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.session.Configuration;
import org.example.mpp.TableFieldMapping;
import org.example.mpp.TableMapping;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @date 2022/2/23 21:30
 */
@Slf4j
public class TableInfoHelper2 {

    public static TableInfo initTableInfo(MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass) {
        if(modelClass.getAnnotation(TableName.class) != null){
            return TableInfoHelper.initTableInfo(builderAssistant, modelClass);
        }
        TableMapping tableMapping = mapperClass.getAnnotation(TableMapping.class);
        if(tableMapping != null) {

            return getTableInfoFromMapperClass(builderAssistant, tableMapping, mapperClass, modelClass);
            // todo 反射放入 TableInfoHelper 的缓存中？
        }
        return TableInfoHelper.initTableInfo(builderAssistant, modelClass);
    }

    private static TableInfo getTableInfoFromMapperClass(MapperBuilderAssistant builderAssistant, TableMapping tableMapping, Class<?> mapperClass, Class<?> modelClass) {

        // 只能有1个TableId  todo 看看后续怎么支持多个字段
        if(tableMapping.pks().length > 1) {
            throw ExceptionUtils.mpe("@TableId can't more than one in Class: \"%s\".", mapperClass.getName());
        }
        if(tableMapping.pks().length > 0 && tableMapping.pks()[0].value().isEmpty()) {
            throw ExceptionUtils.mpe("@TableId value not be empty in Class: \"%s\".", mapperClass.getName());
        }

        // todo configuration 不同，重新加载？
        final Configuration configuration = builderAssistant.getConfiguration();
        // if (targetTableInfo != null) {
        //     Configuration oldConfiguration = targetTableInfo.getConfiguration();
        //     if (!oldConfiguration.equals(configuration)) {
        //         // 不是同一个 Configuration,进行重新初始化
        //         targetTableInfo = initTableInfo(configuration, builderAssistant.getCurrentNamespace(), clazz);
        //     }
        //     return targetTableInfo;
        // }

        /* 没有获取到缓存信息,则初始化 */
        TableInfo tableInfo = new TableInfo(modelClass);
        // todo 访问级别是package的！！！
        tableInfo.setCurrentNamespace(builderAssistant.getCurrentNamespace());
        tableInfo.setConfiguration(configuration);
        GlobalConfig globalConfig = GlobalConfigUtils.getGlobalConfig(configuration);

        /* 初始化表名相关 */
        final String[] excludeProperty = initTableName(tableInfo, tableMapping, globalConfig);

        /* 开启了自定义 KEY 生成器 */
        if (null != globalConfig.getDbConfig().getKeyGenerator()) {
            tableInfo.setKeySequence(mapperClass.getAnnotation(KeySequence.class));
        }

        List<String> excludePropertyList = excludeProperty != null && excludeProperty.length > 0 ? Arrays.asList(excludeProperty) : Collections.emptyList();

        /* 初始化字段相关 */
        initTableFields(tableMapping, modelClass, globalConfig, tableInfo, excludePropertyList);

        /* 自动构建 resultMap */
        tableInfo.initResultMapIfNeed();

        // TODO  放置缓存
        // TABLE_INFO_CACHE.put(clazz, tableInfo);
        // TABLE_NAME_INFO_CACHE.put(tableInfo.getTableName(), tableInfo);
        putCache(modelClass, tableInfo);

        /* 缓存 lambda */
        LambdaUtils.installCache(tableInfo);
        return tableInfo;
    }

    /**
     * 初始化 表数据库类型,表名,resultMap
     * @param tableInfo
     * @param tableMapping
     * @param globalConfig
     * @return
     */
    private static String[] initTableName(TableInfo tableInfo, TableMapping tableMapping, GlobalConfig globalConfig) {
        GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
        TableName table = tableMapping.name();
        String tableName = table.value();
        String tablePrefix = dbConfig.getTablePrefix();
        String schema = dbConfig.getSchema();
        boolean tablePrefixEffect = true;
        String[] excludeProperty = null;
        if (StringUtils.isNotBlank(tablePrefix) && !table.keepGlobalPrefix()) {
            tablePrefixEffect = false;
        }
        if (StringUtils.isNotBlank(table.schema())) {
            schema = table.schema();
        }
        if (StringUtils.isNotBlank(table.resultMap())) {
            tableInfo.setResultMap(table.resultMap());
        }
        tableInfo.setAutoInitResultMap(table.autoResultMap());
        excludeProperty = table.excludeProperty();
        String targetTableName = tableName;
        if (StringUtils.isNotBlank(tablePrefix) && tablePrefixEffect) {
            targetTableName = tablePrefix + targetTableName;
        }
        if (StringUtils.isNotBlank(schema)) {
            targetTableName = schema + StringPool.DOT + targetTableName;
        }
        tableInfo.setTableName(targetTableName);
        return excludeProperty;
    }

    private static void initTableFields(TableMapping tableMapping, Class<?> clazz, GlobalConfig globalConfig, TableInfo tableInfo, List<String> excludeProperty) {
        /* 数据库全局配置 */
        GlobalConfig.DbConfig dbConfig = globalConfig.getDbConfig();
        ReflectorFactory reflectorFactory = tableInfo.getConfiguration().getReflectorFactory();
        Reflector reflector = reflectorFactory.findForClass(clazz);
        TableFieldMapping[] fieldMappings = tableMapping.fields();
        TableId tableId = tableMapping.pks().length > 0 ? tableMapping.pks()[0] : null;

        Map<String, TableField> fieldMap = new HashMap<>(fieldMappings.length);
        for(TableFieldMapping fieldMapping : fieldMappings) {
            fieldMap.put(fieldMapping.value(), fieldMapping.field());
        }
        List<Field> fieldList = ReflectionKit.getFieldList(ClassUtils.getUserClass(clazz));
        List<TableFieldInfo> fieldInfoList = new ArrayList<>(fieldList.size());

        // 标记是否读取到主键
        boolean isReadPK = false;

        for (Field field : fieldList) {
            if (excludeProperty.contains(field.getName())) {
                continue;
            }
            String property = field.getName();
            TableField tableField = fieldMap.get(property);
            if(tableField != null && !tableField.exist()) {
                continue;
            }
            if(!isReadPK) {
                if(tableId != null) {
                    String fieldName = fieldName(field, tableInfo);
                    String column = tableId.value();
                    if(column.equalsIgnoreCase(fieldName)){
                        /* 主键策略（ 注解 > 全局 ） */
                        // 设置 Sequence 其他策略无效
                        tableInfo.setIdType(IdType.NONE == tableId.type() ? dbConfig.getIdType() : tableId.type());
                        key(tableInfo, dbConfig.isCapitalMode() ? column.toUpperCase() : column, property, reflector);
                        isReadPK = true;
                        continue;
                    }
                }else if("id".equalsIgnoreCase(property)){
                    tableInfo.setIdType(dbConfig.getIdType());
                    key(tableInfo, dbConfig.isCapitalMode() ? property.toUpperCase() : property, property, reflector);
                    isReadPK = true;
                    continue;
                }
            }

            // 是否存在 @TableLogic 注解 todo 不支持逻辑删除
            // boolean existTableLogic = isExistTableLogic(list);
            boolean existTableLogic = false;

            /* 有 @TableField 注解的字段初始化 */
            if (tableField != null) {
                fieldInfoList.add(new TableFieldInfo(dbConfig, tableInfo, field, tableField, reflector, existTableLogic));
                continue;
            }
            /* 无 @TableField 注解的字段初始化 */
            fieldInfoList.add(new TableFieldInfo(dbConfig, tableInfo, field, reflector, existTableLogic));
        }

        /* 字段列表 */
        tableInfo.setFieldList(fieldInfoList);

        /* 未发现主键注解，提示警告信息 */
        if (!isReadPK) {
            log.warn(String.format("Can not find table primary key in Class: \"%s\".", clazz.getName()));
        }

    }


    // 从modelClass的属性推断字段名称
    private static String fieldName(Field field, TableInfo tableInfo) {
        final String property = field.getName();
        String fieldName = tableInfo.isUnderCamel() ? StringUtils.camelToUnderline(property) : property;
        return fieldName;
    }

    // 设置主键
    private static void key(TableInfo tableInfo, String column, String property, Reflector reflector) {
        final Class<?> keyType = reflector.getGetterType(property);
        if (keyType.isPrimitive()) {
            log.warn(String.format("This primary key of \"%s\" is primitive !不建议如此请使用包装类 in Class: \"%s\"",
                    property, tableInfo.getEntityType().getName()));
        }
        tableInfo.setKeyRelated(TableInfoHelper.checkRelated(tableInfo.isUnderCamel(), property, column))
                .setKeyColumn(column)
                .setKeyProperty(property)
                .setKeyType(keyType);
    }

    private static void putCache(Class<?> modelClass, TableInfo tableInfo) {
        if(tableInfo == null) {
            return;
        }
        try {
            Field field = TableInfoHelper.class.getDeclaredField("TABLE_INFO_CACHE");
            if(field == null) {
                log.warn("TableInfoHelper TABLE_INFO_CACHE not exist!");
                return;
            }
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            Map<Class<?>, TableInfo> map = (Map<Class<?>, TableInfo>) field.get(TableInfoHelper.class);
            map.put(modelClass, tableInfo);
            field = TableInfoHelper.class.getDeclaredField("TABLE_NAME_INFO_CACHE");
            if(field == null) {
                log.warn("TableInfoHelper TABLE_NAME_INFO_CACHE not exist!");
                return;
            }
            Map<String, TableInfo> map2 = (Map<String, TableInfo>) field.get(TableInfoHelper.class);
            map2.put(tableInfo.getTableName(), tableInfo);
        }catch (NoSuchFieldException | IllegalAccessException e) {
            log.warn("putCache fail.", e);
        }
    }
}
