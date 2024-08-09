package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}