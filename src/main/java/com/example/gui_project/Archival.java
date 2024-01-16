package com.example.gui_project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Archival {
    static Fileworker flw;
    Archival(Fileworker file){
        flw = file;
    }
    static void Archive(String Name, String Format){
        flw.FileWrite(Name, Format);
        try(ZipOutputStream out = new ZipOutputStream(new FileOutputStream(Name + ".zip")); FileInputStream fis = new FileInputStream(Name + '.' + Format)){
            ZipEntry enter_one = new ZipEntry(Name + '.' + Format);
            out.putNextEntry(enter_one);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            out.write(buffer);
            out.closeEntry();
            fis.close();
            Path path = Path.of("C:\\Users\\user\\IdeaProjects\\Gui_Project\\" + Name + '.' + Format);
            try{
                Files.delete(path);
            }catch (IOException e) {
                System.err.println("Не удалось удалить файл: " + e.getMessage());
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
