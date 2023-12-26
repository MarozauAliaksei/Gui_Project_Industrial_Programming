package com.example.gui_project;

import javafx.scene.control.Alert;
import netscape.javascript.JSObject;

import java.beans.XMLDecoder;
import java.io.*;
import java.util.Objects;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Fileworker {
    String filename_;
    String format_;
    Vector<String> content_;
    Vector<Information> inf_ = new Vector<Information>();
    Fileworker() {
        filename_ = "";
        format_ = "txt";
        content_ = null;
    }

    Fileworker(String filename, String format) {
        filename_ = filename;
        format_ = format;
        content_ = null;
    }

    void Fill_inf() {
        Pattern regex_var = Pattern.compile("([A-z])\\s=\\s(-?\\d+)");
        Pattern regex_task_txt = Pattern.compile("Task (\\d+)");
        String tmp = content_.get(0);
        int i = 2;
        Matcher mat = regex_task_txt.matcher(tmp);
        if (mat.find()) {
            Information a = new Information(Integer.parseInt(mat.group(1)));
            a.task_ = content_.get(1);
            for (; i < content_.size(); i++) {
                tmp = content_.get(i);
                mat = regex_task_txt.matcher(tmp);
                Matcher mm = regex_var.matcher(tmp);
                if (mat.find()) {
                    inf_.add(a);
                    a = new Information(Integer.parseInt(mat.group(1)));
                    i++;
                    a.task_ = content_.get(i);
                    continue;
                }
                if (mm.find()) {
                    a.variables_.put(mm.group(1).charAt(0), Double.valueOf(mm.group(2)));
                    continue;
                }
            }
            inf_.add(a);
        }
    }

    void Open_zip() {

    }


    void ReadFromFile(Boolean my_method) throws FileNotFoundException {
        content_ = new Vector<String>();
        String name = filename_.concat(".");
        name = name.concat(format_);
        switch (format_) {
            case ("txt"):
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(name));
                    String line = reader.readLine();
                    while (line != null) {
                        content_.add(line);
                        line = reader.readLine();
                    }
                    reader.close();
                } catch (IOException e) {
                    Alert file_not_found = new Alert(Alert.AlertType.ERROR);
                    file_not_found.setTitle("ERROR");
                    file_not_found.setHeaderText("Error file is not found!");
                    file_not_found.setContentText("Check file name");
                    file_not_found.show();
                }
                return;
            case ("json"):
                if(my_method){
                Pattern regex_var_jsn = Pattern.compile("\"([A-Z]|[a-z])\":\\s(-?\\d+)");
                Pattern regex_jsn = Pattern.compile("\"task_number\":\\s(\\d+)");
                Pattern regex_eq_jsn = Pattern.compile("\"equation\":\\s\"(.+)\"");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(name));
                    String line = reader.readLine();
                    while (line != null) {
                        Matcher mat = regex_jsn.matcher(line);
                        if (mat.find()) {
                            content_.add("Task " + mat.group(1));
                            line = reader.readLine();
                            mat = regex_eq_jsn.matcher(line);
                            if (mat.find()) {
                                content_.add(mat.group(1));
                                line = reader.readLine();
                                line = reader.readLine();
                                mat = regex_var_jsn.matcher(line);
                                while (mat.find()) {
                                    content_.add(mat.group(1) + " = " + mat.group(2));
                                    line = reader.readLine();
                                    mat = regex_var_jsn.matcher(line);
                                }
                            }
                        }
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    Alert file_not_found = new Alert(Alert.AlertType.ERROR);
                    file_not_found.setTitle("ERROR");
                    file_not_found.setHeaderText("Error file is not found!");
                    file_not_found.setContentText("Check file name");
                    file_not_found.show();
                }
                }
                else {

                }
                return;

            case ("xml"):
                if (my_method){
                    Pattern regex_var_xml = Pattern.compile("<([A-Z]|[a-z])>(-?\\d+)");
                    Pattern regex_xml = Pattern.compile("<task task_number=\"(\\d+)\">");
                    Pattern regex_eq_xml = Pattern.compile("<equation>(.+)</equation>");
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(name));
                        String line = reader.readLine();
                        while (line != null) {
                            Matcher mat = regex_xml.matcher(line);
                            if (mat.find()) {
                                content_.add("Task " + mat.group(1));
                                line = reader.readLine();
                                mat = regex_eq_xml.matcher(line);
                                if (mat.find()) {
                                    content_.add(mat.group(1));
                                    line = reader.readLine();
                                    line = reader.readLine();
                                    mat = regex_var_xml.matcher(line);
                                    while (mat.find()) {
                                        content_.add(mat.group(1) + " = " + mat.group(2));
                                        line = reader.readLine();
                                        mat = regex_var_xml.matcher(line);
                                    }
                                }
                            }
                            line = reader.readLine();
                        }
                    } catch (IOException e) {
                        Alert file_not_found = new Alert(Alert.AlertType.ERROR);
                        file_not_found.setTitle("ERROR");
                        file_not_found.setHeaderText("Error file is not found!");
                        file_not_found.setContentText("Check file name");
                        file_not_found.show();
                    }

                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + format_);
        }
    }

    void PrintContent() {
        for (Information information : inf_) {
            information.Change_var();
            String tmp = information.Remove_bruh(information.task_without_var);
            if(information.solvebale){
                information.result = Double.parseDouble(tmp);
                information.CoutAll();
            }
            else {
                information.CoutError(tmp);
            }

        }
    }

    void FileWrite(String Filename, String Format) {
        switch (Format) {
            case ("txt"):
                try (FileWriter writer = new FileWriter(Filename + "." + Format, false)) {
                    for (Information temp : inf_) {
                        writer.write("Task " + temp.task_number_ + '\n');
                        if(temp.solvebale) {
                            writer.write("Answer: " + temp.result + '\n');
                        }
                        else {
                            writer.write("Division by zero");
                        }
                        writer.flush();
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case ("json"):
                try (FileWriter writer = new FileWriter(Filename + '.' + Format, false)) {
                    writer.write("{\n");
                    writer.write("  \"tasks\": [\n");
                    int it = inf_.size();
                    for (Information tmp : inf_) {
                        writer.write("    {\n");
                        it--;
                        writer.write("      \"task_number\": " + tmp.task_number_ + ",\n");
                        if(tmp.solvebale) {
                            writer.write("      \"answer\": " + tmp.result + '\n');
                        }
                        else {
                            writer.write("      \"answer\": \"Division by zero\"");
                        }
                        if(it != 0){
                            writer.write("    },\n");
                        }
                        else {
                            writer.write("    }\n");
                        }
                    }
                    writer.write("  ]\n");
                    writer.write("}");
                }
                catch (IOException ex){
                    System.out.println(ex.getMessage());
                }
                break;
            case ("xml"):
                try(FileWriter writer = new FileWriter(Filename + "." + Format, false)) {
                    writer.write("<tasks>");
                    for (var it : inf_){
                        writer.write(   "<task task_number =\"" + it.task_number_ + "\">\n");
                        writer.write("      <answer>" + it.result + "</answer>\n");
                        writer.write("    </task>\n");
                    }
                    writer.write("</tasks>");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            default:
                throw new IllegalStateException("Unexpected value: " + Format);
        }
    }
}