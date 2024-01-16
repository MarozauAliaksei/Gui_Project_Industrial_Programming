package com.example.gui_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
    private static Clip clip;
    public static void playSound(String soundFilePath, int startFromMillis) {    try {
        File soundFile = new File(soundFilePath);        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);        clip.setMicrosecondPosition(startFromMillis * 1000);
        clip.start();    } catch (Exception e) {
        e.printStackTrace();    }
    }
    public static void stopSound() {    if (clip != null && clip.isRunning()) {
        clip.stop();    }
    }
    public CheckBox To_Encode;
    public TextField Key;
    public CheckBox To_decrypt;
    public TextField Key_dec;
    Boolean was_read = Boolean.FALSE;
    public RadioButton out_json;
    public RadioButton out_xml;
    public CheckBox arch;
    public RadioButton out_txr;
    public String Out_File_Name;
    public String Out_File_Format;
    @FXML
    public RadioButton inptxml;
    @FXML
    public RadioButton inptzip;
    @FXML
    public TextField out_file_name;
    Fileworker a = new Fileworker();
    Vector<Fileworker> arr = new Vector<>();
    String inputtext;
    String Format = "";
    @FXML
    private Label Hello;
    @FXML
    private TextField inptfilename;
    @FXML
    private RadioButton inpttxt;
    @FXML
    private RadioButton inptjson;
    @FXML
    protected void setInpttext(){
        inputtext = inptfilename.getText();
        Hello.setText(inputtext);
    }

    public void Inptgettext(InputMethodEvent inputMethodEvent) {
        inputtext = inptfilename.getText();
        Hello.setText(inputtext);
    }

    public void OnCheckClicked(ActionEvent actionEvent) throws Exception {
        inputtext = inptfilename.getText();
        boolean flag = false;

        Hello.setText(inputtext + Format);
        if(inpttxt.isSelected()){
            Format = "txt";
        } else if (inptjson.isSelected()) {
            Format = "json";
        } else if (inptxml.isSelected()) {
            Format = "xml";
        }
        else
        {
            Format = "zip";
            flag = true;
        }
        if (To_decrypt.isSelected()) {
            File fl = new File(inputtext + "Decrypted" + '.' + Format);
            Decrypt.decryptFile(Key_dec.getText(), inputtext + '.' + "enc", fl);
            inputtext = inputtext + "Decrypted";
        }
        if (!flag) {
            a = new Fileworker(inputtext, Format);
            try {
                a.ReadFromFile(Boolean.TRUE);
            } catch (FileNotFoundException e) {
                Alert file_not_found = new Alert(Alert.AlertType.ERROR);
                file_not_found.setTitle("ERROR");
                file_not_found.setHeaderText("Error file is not found!");
                file_not_found.setContentText("Check file name");
                file_not_found.show();
                return;
            }
            a.Fill_inf();
            for (Information information : a.inf_) {
                information.Change_var();
                String tmp = information.Remove_bruh(information.task_without_var);
                if (information.solvebale) {
                    information.result = Double.parseDouble(tmp);
                } else {
                    information.CoutError(tmp);
                }

            }
        }
        else {
            File folder = new File("Dearch/");

            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            if (file.delete()) {
                                System.out.println("Файл удален: " + file.getName());
                            } else {
                                System.out.println("Не удалось удалить файл: " + file.getName());
                            }
                        }
                    }
                } else {
                    System.out.println("Произошла ошибка при получении списка файлов.");
                }
            }
            Dearchivizer.unzip(inputtext + '.' + Format, "Dearch/");
            File[] files = folder.listFiles();
            if(files != null) {
                for (File file : files) {
                    String name = file.getName();
                    Pattern name_format = Pattern.compile("(.+)\\.(.+)");
                    Matcher fl_name = name_format.matcher(name);
                    if(fl_name.find()){
                        String nm = fl_name.group(1);
                        String fl = fl_name.group(2);

                        Fileworker flw = new Fileworker("Dearch\\" + nm, fl);
                        flw.ReadFromFile(Boolean.TRUE);
                        flw.Fill_inf();
                        for (Information information : flw.inf_) {
                            information.Change_var();
                            String tmp = information.Remove_bruh(information.task_without_var);
                            if (information.solvebale) {
                                information.result = Double.parseDouble(tmp);
                            } else {
                                information.CoutError(tmp);
                            }
                        }
                        arr.add(flw);
                    }
                    else {
                        Alert wat = new Alert(Alert.AlertType.ERROR);
                        wat.show();
                    }
                }
            }

        }
        was_read = Boolean.TRUE;
        Alert all_good = new Alert(Alert.AlertType.INFORMATION);
        all_good.setTitle("Completed");
        all_good.setHeaderText("File read succession");
        all_good.show();

    }

    public void Create_file(ActionEvent actionEvent) throws Exception {
        if (!was_read){
            Alert wasnt_read = new Alert(Alert.AlertType.ERROR);
            wasnt_read.setTitle("File ERROR");
            wasnt_read.setHeaderText("File wasn't read");
            wasnt_read.setContentText("Try to reread your file");
            wasnt_read.show();
            return;
        }
        Out_File_Name = out_file_name.getText();
        if(out_txr.isSelected()){
            Out_File_Format = "txt";
        } else if (out_json.isSelected()) {
            Out_File_Format = "json";
        } else {
            Out_File_Format = "xml";
        }
        if (!Objects.equals(Format, "zip")) {
            Archival arc = new Archival(a);
            if (arch.isSelected()) {
                arc.Archive(Out_File_Name, Out_File_Format);
            } else {
                a.FileWrite(Out_File_Name, Out_File_Format);
            }
            if (To_Encode.isSelected()) {
                File enc = new File(Out_File_Name + ".enc");
                Encryptor.encryptFile(Key.getText(), Out_File_Name + '.' + Out_File_Format, enc);
            }
        }
        else {
            Out_File_Name = "OutputFromArch\\" + Out_File_Name;
            int counter = 0;
            for (Fileworker flw : arr){
                counter++;
                Archival arc = new Archival(flw);
                if(arch.isSelected()){
                    arc.Archive(Out_File_Name + counter, Out_File_Format);
                } else {
                    flw.FileWrite(Out_File_Name + counter, Out_File_Format);
                }
                if (To_Encode.isSelected()){
                    File enc = new File(Out_File_Name + counter + ".enc");
                    Encryptor.encryptFile(Key.getText(), Out_File_Name + counter + "." + Out_File_Format, enc);
                }
            }
        }
    }
}