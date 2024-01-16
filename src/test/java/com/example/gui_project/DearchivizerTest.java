package com.example.gui_project;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class DearchivizerTest {

    @Test
    void testUnzip() {
        try {
            File tempArchive = createTempZipArchive();

            File tempExtractFolder = createTempFolder();

            Dearchivizer.unzip(tempArchive.getAbsolutePath(), tempExtractFolder.getAbsolutePath());

            assertTrue(tempExtractFolder.exists());
            assertTrue(tempExtractFolder.isDirectory());

            File extractedFile = new File(tempExtractFolder, "testFile.txt");
            assertTrue(extractedFile.exists());
            assertTrue(extractedFile.isFile());

        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    private File createTempZipArchive() throws IOException {
        File tempFile = File.createTempFile("testArchive", ".zip");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(tempFile))) {
            ZipEntry entry = new ZipEntry("testFile.txt");
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write("Hello, World!".getBytes());
            zipOutputStream.closeEntry();
        }
        return tempFile;
    }

    private File createTempFolder() throws IOException {
        return Files.createTempDirectory("testExtractFolder").toFile();
    }

    // Добавьте другие тесты в зависимости от ваших требований
}
