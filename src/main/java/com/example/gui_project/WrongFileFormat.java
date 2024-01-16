package com.example.gui_project;

import javafx.scene.control.Alert;

public class WrongFileFormat {
    public static void Wrong(){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Wrong File format");
        a.setContentText("Check file \"Example + your type of input file\"");
        a.setTitle("Error");
        a.show();

    }
}
