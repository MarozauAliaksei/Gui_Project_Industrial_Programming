package com.example.gui_project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

class EncryptorTest {

    @Test
    void testEncryptFile() {
        try {
            String key = "asdfasdfasdfasdf"; // Поменяйте на реальный ключ
            File inputFile = new File("Input.txt"); // Укажите путь к тестовому входному файлу
            File outputFile = new File("outm.enc"); // Укажите путь к тестовому выходному файлу

            Encryptor.encryptFile(key, inputFile.getPath(), outputFile);

            assertTrue(outputFile.exists());
            assertTrue(outputFile.length() > 0);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testEncryptAndDecrypt() {
        try {
            String key = "asdfasdfasdfasdf";
            File inputFile = new File("Input.txt");
            File encryptedFile = new File("Input.enc");
            File decryptedFile = new File("Input.txt");
            Encryptor.encryptFile(key, inputFile.getPath(), encryptedFile);
            Decrypt.decryptFile(key, encryptedFile.getPath(), decryptedFile);

            assertTrue(decryptedFile.exists());
            assertTrue(decryptedFile.length() > 0);

            byte[] originalBytes = Files.readAllBytes(Paths.get(inputFile.getPath()));
            byte[] decryptedBytes = Files.readAllBytes(Paths.get(decryptedFile.getPath()));

            assertArrayEquals(originalBytes, decryptedBytes);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

}
