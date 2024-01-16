package com.example.gui_project;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ArchivalTest {

    @Test
    void archive() throws IOException {
        Fileworker flw = new Fileworker("Example", "txt");
        Archival arc = new Archival(flw);
        arc.Archive("Example", "txt");
        File arch = new File("Example.zip");
        if(arch.exists()){
            Dearchivizer.unzip("Example", "Dearch");
            Scanner in1 = new Scanner("Dearch\\Example.txt");
            Scanner in2 = new Scanner("Example.txt");
            boolean flag = true;
            String str1 = in1.nextLine();
            String str2 = in2.nextLine();
            while(Objects.equals(str1, str2)){
                str1 = in1.nextLine();
                str2 = in2.nextLine();
                if(str1.isEmpty() && str2.isEmpty()){
                    flag = true;
                }
            }
            assertTrue(flag);
        }
    }
}