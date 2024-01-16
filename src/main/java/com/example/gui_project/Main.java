package com.example.gui_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("CallToPrintStackTrace")
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Scene scene = new Scene(root, 720, 600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Boolean IsAvailableFormat(String st){
        return switch (st) {
            case ("zip"), ("json"), ("txt"), ("xml"), ("enc") -> true;
            default -> false;
        };
    }


    public static void main(String[] args) throws Exception {
        launch();
        System.out.println("Do you want to use console? (Y/N)");
        Scanner in = new Scanner(System.in);
        String chr = in.nextLine();
        while(Objects.equals(chr, "Y")){
            System.out.println("Input file name (Available txt, json, zip, enc(for encoded)");
            String file = in.nextLine();
            Pattern reg_file = Pattern.compile("([A-Za-z0-9-_,\\s]+)[.]([A-Za-z]+$)");
            Matcher mat =reg_file.matcher(file);
            if(mat.find() && IsAvailableFormat(mat.group(2))){
                String name = mat.group(1);
                String format = mat.group(2);
                if(Objects.equals(format, "enc")){
                    System.out.println("What was type of your file before encoding?");
                    format = in.nextLine();
                    System.out.println("Input 16 character key");
                    String Key = in.nextLine();
                    if(Key.length() != 16){
                        System.out.println("Error in Key, do you want to continue&(Y/N)");
                        chr = in.nextLine();
                        continue;
                    }
                    File fl = new File((name + "Decrypted" + '.' + format));
                    Decrypt.decryptFile(Key, name + ".enc", fl);
                    name = name + "Decrypted";
                }
                Fileworker a;
                a = new Fileworker(name, format);
                a.ReadFromFile(true);
                a.Fill_inf();
                a.PrintContent();
                System.out.println("Insert Output File name: ");
                String output = in.nextLine();
                mat = reg_file.matcher(output);

                if(mat.find()){
                    name = mat.group(1);
                    String name_cnst = name;
                    format = mat.group(2);
                    System.out.println("Архвировать файл? (Y/N)");
                    String inpt = in.nextLine();
                    while (!Objects.equals(inpt, "Y") && !Objects.equals(inpt, "N")){
                        System.out.println("Некорректный ввод");
                    }
                    if (inpt.equals("Y")) {
                            Archival.Archive(name, format);
                        }

                    else {
                    a.FileWrite(mat.group(1), mat.group(2));
                    }
                    System.out.println("Encrypt File?(Y/N)");
                    chr = in.nextLine();
                    if(chr.equals("Y")){
                        System.out.println("Input 16-digit Key");
                        String Key = in.nextLine();
                        File enc = new File(name + ".enc");
                        Encryptor.encryptFile(Key, name + '.' + format ,enc);
                    }
                    System.out.println("Do you want to continue?");
                    chr = in.nextLine();

            }else {
                System.out.println("Error in file name, do you want to continue&(Y/N)");
                chr = in.nextLine();
            }

        }
    }
}
}