module com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator to javafx.fxml;
    exports com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;
}
