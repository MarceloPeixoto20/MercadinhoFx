module com.mycompany.mercadinho {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;
    opens com.mycompany.mercadinho.model to javafx.base;
    opens com.mycompany.mercadinho to javafx.fxml;
    exports com.mycompany.mercadinho;
}
