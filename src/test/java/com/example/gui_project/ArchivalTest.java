package com.example.gui_project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipFile;

class ArchivalTest {

    @Test
    void testArchive() {
        try {
            String fileName = "testFile";
            String fileFormat = "txt";
            Fileworker fileworker = new Fileworker(); // Предполагаем, что у вас есть реализация Fileworker
            Archival archival = new Archival(fileworker);

            archival.Archive(fileName, fileFormat);

            File archivedFile = new File("C:\\Users\\user\\IdeaProjects\\Gui_Project\\" + fileName + ".zip");

            assertTrue(archivedFile.exists());
            assertTrue(archivedFile.isFile());

            try (ZipFile zipFile = new ZipFile(archivedFile)) {
                assertTrue(zipFile.entries().hasMoreElements());
            }
        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

}
