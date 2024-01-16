package com.example.gui_project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

class DecryptTest {

    @Test
    void testDecryptFile() {
        try {
            String key = "asdfasdfasdfasdf";
            File inputFile = new File("BOOO.enc");
            File outputFile = new File("Test.json");

            Decrypt.decryptFile(key, inputFile.getPath(), outputFile);

            assertTrue(outputFile.exists());
            assertTrue(outputFile.length() > 0);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testDecryptFileWithInvalidKey() {
        try {
            String key = "INVALIDKEY123456";
            File inputFile = new File("BOOO.enc"); // Укажите путь к тестовому входному файлу
            File outputFile = new File("A.json"); // Укажите путь к тестовому выходному файлу

            Decrypt.decryptFile(key, inputFile.getPath(), outputFile);

            fail("Exception not thrown for invalid key");
        } catch (Exception e) {
            // Ожидается исключение, тест успешен
        }
    }
}
