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

    opens br.edu.ufersa.avance.model.entities to org.hibernate.orm.core;

    exports br.edu.ufersa.avance.controller;
    opens br.edu.ufersa.avance.controller to javafx.fxml;

    exports br.edu.ufersa.avance.view;
    opens br.edu.ufersa.avance.view to javafx.fxml;
    exports br.edu.ufersa.avance.controller.noimpl;
    opens br.edu.ufersa.avance.controller.noimpl to javafx.fxml;
}