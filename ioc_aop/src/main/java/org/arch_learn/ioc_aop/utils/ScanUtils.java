package org.arch_learn.ioc_aop.utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ScanUtils {
    private static Set<String> classNames = new HashSet<String>();

    public static void doScanPackage(String rootPath) {
        String scanPackagePath = Thread.currentThread().getContextClassLoader()
                .getResource("").getPath() + rootPath.replaceAll("\\.", "/");

        File file = new File(scanPackagePath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                doScanPackage(rootPath + "." + f.getName());
            } else if (f.getName().endsWith(".class")) {
                String name = f.getName().replaceAll("\\.class", "");
                String className = rootPath + "." + name;
                classNames.add(className);
            }
        }
    }

    public static Set<String> getClassNames() {
        return classNames;
    }
}
