package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
