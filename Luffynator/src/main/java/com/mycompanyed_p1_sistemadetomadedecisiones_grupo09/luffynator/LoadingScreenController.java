/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author asala
 */
public class LoadingScreenController implements Initializable {
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Text loadingText;

    private LoadingScreen loadingScreen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadingScreen = new LoadingScreen(progressIndicator, loadingText);
        startProgress(); // Inicia el progreso automáticamente
    }

    private void startProgress() {
        Thread thread = new Thread(loadingScreen);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void restart() {
        progressIndicator.setProgress(0);
        loadingText.setText("Loading...");
        startProgress(); // Reinicia el progreso cuando se hace clic en el botón "R"
    }

    // Loading screen runnable class
    public class LoadingScreen implements Runnable {

        private ProgressIndicator progressIndicator;
        private Text loadingText;

        public LoadingScreen(ProgressIndicator progressIndicator, Text loadingText) {
            this.progressIndicator = progressIndicator;
            this.loadingText = loadingText;
        }

        @Override
        public void run() {
            while(progressIndicator.getProgress() < 1.0) {
                Platform.runLater(() -> progressIndicator.setProgress(progressIndicator.getProgress() + 0.1));
                try {
                    Thread.sleep(100); // Ajusta el tiempo de espera entre cada incremento
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> loadingText.setText("Éxito"));
        }
    }
}