package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * gkislin
 * 21.07.2016
 */
public class MainFile {
    private static final Comparator<File> filesAndDirComparator = (o1, o2) -> {
        if (o1.isFile() && o2.isDirectory()) {
            return 1;
        }
        if (o1.isDirectory() && o2.isFile()) {
            return -1;
        }
        return 0;
    };

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\n======================================================");
        System.out.println("Рекурсивный вывод имен файлов:");
        printFileNames(dir, "");
    }

    private static void printFileNames(File file, String indent) {
        if (file.isFile()) {
            System.out.println(indent + file.getName());
        } else if (file.isDirectory()) {
            System.out.println(indent + file.getName());
            File[] files = file.listFiles();
            if (files != null) {
                List<File> list = Arrays.stream(files).sorted(filesAndDirComparator).toList();
                for (File f : list) {
                    printFileNames(f, indent + "  ");
                }
            }
        }
    }
}