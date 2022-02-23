package x.xx.utils;

import x.xx.dto.CodeNameDTO;
import x.xx.enums.DictLabel;
import x.xx.enums.ICodeName;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DictUtils {

    private static final Map<String, List<CodeNameDTO>> dictCacheMap;

    static {
        dictCacheMap = loadDict("x.xx");
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getAllDict());
    }

    /**
     * 获取所有字典类枚举
     */
    public static Map<String, List<CodeNameDTO>> getAllDict() {
        return dictCacheMap;
    }

    /**
     * 从指定包路径加载字典类枚举
     */
    private static Map<String, List<CodeNameDTO>> loadDict(String packageName) {
        Map<String, List<CodeNameDTO>> map = new HashMap<>();

        try {
            List<String> classNames = scanClasses(packageName);
            for(String className : classNames) {
                Class clazz = Class.forName(className);
                // 如果是枚举类，且是定义的字典类枚举接口ICodeName
                if(clazz.isEnum() && ICodeName.class.isAssignableFrom(clazz)) {
                    DictLabel dictLabel = (DictLabel) clazz.getAnnotation(DictLabel.class);
                    // 默认使用类名称作为字典类型名称
                    String label = clazz.getSimpleName();
                    // 如果有DictLabel注解，则用注解中的分类名称
                    if(dictLabel != null && !dictLabel.value().isEmpty()) {
                        label = dictLabel.value();
                    }
                    List<CodeNameDTO> list = new ArrayList<>();
                    Object[] values = clazz.getEnumConstants();
                    CodeNameDTO codeNameDTO = null;
                    for(Object value : values) {
                        ICodeName en = (ICodeName) value;
                        codeNameDTO = new CodeNameDTO();
                        codeNameDTO.setCode(en.getCode());
                        codeNameDTO.setName(en.getName());
                        list.add(codeNameDTO);
                    }
                    map.put(label, Collections.unmodifiableList(list));
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.unmodifiableMap(map);
    }


    /**
     * 扫描包下所有类名称
     * @param packageName 包名称
     * @return 类名称列表
     * @throws IOException
     */
    private static List<String> scanClasses(String packageName) throws IOException {
        List<String> list = new ArrayList<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (urlEnumeration.hasMoreElements()) {
            URL url = urlEnumeration.nextElement();
            String protocol = url.getProtocol();
            if("jar".equals(protocol)){
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String jarEntryName = entry.getName();
                    if(jarEntryName.startsWith(packageDirName) && jarEntryName.endsWith(".class")) {
                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                        list.add(className);
                    }
                }

            }else if("file".equals(protocol)){
                // 递归查找所有路径下的类
                findClass(packageName, new File(url.getFile()), list);
            }
        }
        return list;
    }

    /**
     * 找到路径下的所有类
     * @param packageName 当前路径对应的包名
     * @param file 当前路径的File对象
     * @param classNames 输出的类名称列表
     */
    private static void findClass(String packageName, File file, List<String> classNames) {
        String name = file.getName();
        if(file.isDirectory()) {
            File[] subFiles = file.listFiles();
            for(File subFile : subFiles) {
                String subPackageName = packageName + (subFile.isDirectory() ? "." + subFile.getName() : "");
                findClass(subPackageName, subFile, classNames);
            }
        }else if(name.endsWith(".class")){
            String className = packageName + "." + name.substring(0, name.length() - 6);
            classNames.add(className);
        }
    }


}
