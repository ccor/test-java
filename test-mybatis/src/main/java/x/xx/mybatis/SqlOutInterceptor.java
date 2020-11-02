package x.xx.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class SqlOutInterceptor implements Interceptor {

    private static final Pattern PATTERN_BLANK = Pattern.compile("\\s{2,}");
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        String sqlId = getSqlId(invocation);
        String sql = sql(invocation);
        log.info("{}: print cost: {}ms, sql: {}", sqlId, System.currentTimeMillis() - startTime, sql);
        Object result = invocation.proceed();
        log.debug("{} cost: {}ms", sqlId, System.currentTimeMillis() - startTime);
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private String getSqlId(Invocation invocation) {
        StatementHandler stmtHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStmtHandler = SystemMetaObject.forObject(stmtHandler);
        MappedStatement mappedStatement = (MappedStatement) metaStmtHandler.getValue("delegate.mappedStatement");
        return mappedStatement.getId();
    }

    private String sql(Invocation invocation) {
        StatementHandler stmtHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStmtHandler = SystemMetaObject.forObject(stmtHandler);
        BoundSql boundSql = (BoundSql) metaStmtHandler.getValue("delegate.boundSql");
        String sql = boundSql.getSql();
        Object parameterObject = metaStmtHandler.getValue("delegate.boundSql.parameterObject");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        StringBuilder sb = new StringBuilder(sql.length());
        // 去除多余的空白字符
        sql = PATTERN_BLANK.matcher(sql).replaceAll(" ");

        if (parameterMappings != null) {
            Configuration configuration = (Configuration) metaStmtHandler.getValue("delegate.configuration");
            MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            int begin = 0;
            for (ParameterMapping parameterMapping : parameterMappings) {
                int pos = sql.indexOf("?", begin);
                sb.append(sql, begin, pos);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    // 参数值
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    // 获取参数名称
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 获取参数值
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        // 如果是单个值则直接赋值
                        value = parameterObject;
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    sb.append(stringOf(value));
                }
                begin = pos + 1;
            }
            sb.append(sql, begin, sql.length());
            sql = sb.toString();
        }
        return sql;
    }

    private String stringOf(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return String.valueOf(value);
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("'");
            if (value instanceof Date) {
                builder.append(DATE_FORMAT_THREAD_LOCAL.get().format((Date) value));
            } else if (value instanceof String) {
                builder.append(value);
            }
            builder.append("'");
            return builder.toString();
        }
    }
}
