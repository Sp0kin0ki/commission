module org.project.aeroport.app.aeroport_tp {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.web;
    requires java.naming;
    
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    // requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires transitive java.sql;
    requires org.postgresql.jdbc;
    requires transitive  org.hibernate.orm.core;
    requires jakarta.persistence;
    requires javafx.graphics;
    requires transitive javafx.base;

    opens com.example to javafx.fxml;
    opens com.example.entities to javafx.fxml, org.hibernate.orm.core;
    opens com.example.controllers to javafx.fxml;
    opens com.example.dto to javafx.base;

    exports com.example;
    exports com.example.entities;
    exports com.example.controllers;

}