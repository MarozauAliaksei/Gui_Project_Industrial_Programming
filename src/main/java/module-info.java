module com.example.gui_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires jdk.jsobject;
    requires java.management;
    requires java.naming;
    requires org.apache.commons.compress;
    requires java.json;

    opens com.example.gui_project to javafx.fxml;
    exports com.example.gui_project;
}