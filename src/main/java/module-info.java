module br.edu.ufersa.avance.projetoAvance {
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

    opens br.edu.ufersa.avance.projetoAvance.model.entities to org.hibernate.orm.core;

    exports br.edu.ufersa.avance.projetoAvance.controller;
    opens br.edu.ufersa.avance.projetoAvance.controller to javafx.fxml;

    exports br.edu.ufersa.avance.projetoAvance.view;
    opens br.edu.ufersa.avance.projetoAvance.view to javafx.fxml;
}