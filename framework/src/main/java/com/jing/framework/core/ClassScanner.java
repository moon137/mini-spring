package com.jing.framework.core;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author GUO
 * @date 2019/9/24
 */
public class ClassScanner {

    public static final String SEPARATOR = File.separator;
    public static List<Class<?>> scannerClass(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> enumeration = classLoader.getResources(path);
        while (enumeration.hasMoreElements()) {
            URL resource = enumeration.nextElement();
            if (resource.getProtocol().contains("jar")) {
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classList.addAll(getClassesFromJar(jarFilePath, path));
            }else if(resource.getProtocol().contains("file")){
                String filePath = resource.getPath();
                List<Class<?>> classes = new ArrayList<>();
                getClassesFromDirectory(classes,filePath,packageName);
                classList.addAll(classes);
            }else{
                System.out.println("resource.getProtocol()-->"+resource.getProtocol());
            }
        }
        return classList;
    }

    private static void getClassesFromDirectory( List<Class<?>> classes ,String filePath, String packageName) {
        File file = new File(filePath);
        List<File> fileList = Arrays.asList(file.listFiles());
        fileList.forEach(it->{
            if(it.isDirectory()){
                 getClassesFromDirectory(classes,it.getPath(),packageName);
            }else{
                String tmp = it.getPath().replace(SEPARATOR,".");
                String className = tmp.substring(tmp.indexOf(packageName),tmp.length()-6);
                try {
                    classes.add(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        Enumeration<JarEntry> enumeration = jarFile.entries();
        while (enumeration.hasMoreElements()){
            JarEntry jarEntry = enumeration.nextElement();
            String entryName = jarEntry.getName();
            if(entryName.startsWith(path) && entryName.endsWith(".class")){

                String className = entryName.replace("/",".").substring(0,entryName.length()-6);
                classList.add(Class.forName(className));
            }
        }
        return classList;
    }
}
