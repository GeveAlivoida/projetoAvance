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

    opens br.edu.ufersa.avance to javafx.fxml;
    exports br.edu.ufersa.avance;
    exports br.edu.ufersa.avance.controller;
    opens br.edu.ufersa.avance.controller to javafx.fxml;
}