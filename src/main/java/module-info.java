module br.edu.ufersa.avance.projetoavance {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;
    requires itextpdf;

    opens br.edu.ufersa.avance.projetoavance.model.entities to org.hibernate.orm.core;

    exports br.edu.ufersa.avance.projetoavance.controller;
    opens br.edu.ufersa.avance.projetoavance.controller to javafx.fxml;

    exports br.edu.ufersa.avance.projetoavance.view;
    opens br.edu.ufersa.avance.projetoavance.view to javafx.fxml;
}