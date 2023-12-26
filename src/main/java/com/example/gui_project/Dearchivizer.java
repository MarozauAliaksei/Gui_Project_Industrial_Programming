package com.example.gui_project;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.*;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Dearchivizer {
    public static void unzip(String zipFilePath, String extractFolder) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            byte[] buffer = new byte[1024];
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String entryName = sanitizeFileName(zipEntry.getName());
                File entryDestination = new File(extractFolder, entryName);

                if (zipEntry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    try (OutputStream entryOutputStream = new FileOutputStream(entryDestination)) {
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            entryOutputStream.write(buffer, 0, bytesRead);
                        }
                    }

                    // Проверяем, является ли разархивированный файл архивом
                    if (isZipFile(entryDestination)) {
                        // Если архив, рекурсивно разархивируем его
                        unzip(entryDestination.getAbsolutePath(), entryDestination.getParent());
                        // Удаляем оригинальный архив
                        entryDestination.delete();
                    }
                }

                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }

    private static boolean isZipFile(File file) {
        // Простая проверка по расширению файла
        return file.getName().toLowerCase().endsWith(".zip");
    }

    private static String sanitizeFileName(String fileName) {
        // Можете использовать свою логику очистки от недопустимых символов, если это необходимо
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

}