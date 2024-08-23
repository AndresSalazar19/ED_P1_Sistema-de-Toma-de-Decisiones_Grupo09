/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompanyed_p1_sistemadetomadedecisiones_grupo09.luffynator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author asala
 */
public class InicioController implements Initializable {

    @FXML
    private ImageView luffyInicio;
    @FXML
    private ImageView gomuGomuNomi;
        
    @FXML 
    public void play(String fileName){
        MediaPlayerManager.getInstance().play(fileName);
    }

    @FXML
    public void comenzar() throws IOException {
        System.out.println("Comenzando....");
        App.setRoot("opcionesJuego");
    }

    @FXML
    public void configuracion() throws IOException {
        System.out.println("Comenzando....");
        App.setRoot("configuracion");
    }
    

    private void iniciarAnimacion() {
        // Configurar las posiciones iniciales de los ImageView
        gomuGomuNomi.setY(0);
        double height = gomuGomuNomi.getImage().getHeight();

        // Crear una animación simple usando un bucle de animación
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (lastUpdate != 0) {
                    // Calcular el tiempo transcurrido en segundos desde la última actualización
                    double elapsedTime = (now - lastUpdate) / 1_000_000_000.0;

                    // Velocidad de la animación
                    double speed = 500; // pixeles por segundo

                    // Actualizar la posición de la imagen
                    gomuGomuNomi.setY(gomuGomuNomi.getY() + speed * elapsedTime);

                    // Si la imagen sale de la pantalla por la parte inferior, la reinicia desde arriba
                    if (gomuGomuNomi.getY() > height) {
                        gomuGomuNomi.setY(-height);
                    }
                }
                lastUpdate = now;
            }
        };

        animationTimer.start();
    }

        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        play("music/One Piece OST - Nakama no Shirushi da! Sign Of Friendship.mp3");

        // Carga la imagen desde los recursos
        Image image = new Image(getClass().getResourceAsStream("/imagenes/InicioLuffy.png"));
        luffyInicio.setImage(image);
        String preguntasFilePath = GameManager.getInstance().getPreguntasFilePath();
        String respuestasFilePath = GameManager.getInstance().getRespuestasFilePath();
        
        
        Image image2 = new Image(getClass().getResourceAsStream("/imagenes/gomuGomuAkumaNomi.png"));
        gomuGomuNomi.setImage(image2);
        
        iniciarAnimacion();

        try {
            GameManager.getInstance().loadGameData(preguntasFilePath, respuestasFilePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void cambiarArchivoPreguntas() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Stage stage = App.getPrimaryStage();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                File currentPreguntasFile = new File("src/main/java/archivos/ArchivoPreguntas.csv");

                // Leer contenido del archivo TXT
                List<String> lines = Files.readAllLines(selectedFile.toPath());

                // Limpiar y convertir el contenido a formato CSV
                List<String> cleanedLines = cleanFileContentFromTxt(lines);

                if (cleanedLines.isEmpty()) {
                    showErrorAlert("Limpieza Eliminó Todo",
                            "El contenido del archivo TXT no es válido o fue eliminado durante la limpieza.");
                    return;
                }

                // Validar y escribir contenido limpio en el archivo CSV
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentPreguntasFile))) {
                    for (int i = 0; i < cleanedLines.size(); i++) {
                        String line = cleanedLines.get(i);
                        if (i == cleanedLines.size() - 1 && line.trim().isEmpty()) {
                            continue;
                        }
                        writer.write(line);
                        if (i < cleanedLines.size() - 1) {
                            writer.newLine();
                        }
                    }
                }

                System.out.println(
                        "Archivo de preguntas actualizado desde TXT: " + currentPreguntasFile.getAbsolutePath());

                // Mostrar alerta de éxito
                showSuccessAlert("Archivo de Preguntas Actualizado",
                        "El archivo de preguntas se ha actualizado correctamente desde el archivo TXT.");
            }
        } catch (IOException e) {
            showErrorAlert("Error al Actualizar Preguntas",
                    "Ocurrió un error al intentar actualizar el archivo de preguntas.");
        }
    }

    private List<String> cleanFileContentFromTxt(List<String> lines) {
        List<String> cleanedLines = new ArrayList<>();
        for (String line : lines) {
            // Guardar la línea original para depuración
            String originalLine = line;

            // Limpiar caracteres no deseados (e.g., eliminar BOM y otros)
            line = line.replace("\uFEFF", "").replaceAll("[^\\p{ASCII}]", "").trim();

            // Verifica si la línea sigue el formato esperado de preguntas
            if (!line.trim().isEmpty() && isValidPreguntaFormat(line)) {
                cleanedLines.add(line);
            } else {
                System.out.println("Línea eliminada: " + originalLine + " -> " + line);
            }
        }

        // Verificar si después de la limpieza hay líneas válidas
        if (cleanedLines.isEmpty()) {
            showErrorAlert("Limpieza Eliminó Todo",
                    "El contenido del archivo TXT no es válido o fue eliminado durante la limpieza.");
        }
        return cleanedLines;
    }

    private boolean isValidPreguntaFormat(String line) {
        System.out.println("Validando línea: " + line);
        for (char c : line.toCharArray()) {
            System.out.println("Carácter: '" + c + "' Código ASCII: " + (int) c);
        }
        return line.matches("[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ¿?.,!¡ ]+");
    }

    @FXML
    private void cambiarArchivoRespuestas() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            Stage stage = App.getPrimaryStage();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                File currentRespuestasFile = new File("src/main/java/archivos/ArchivoRespuestas.csv");

                // Leer contenido del archivo TXT
                List<String> lines = Files.readAllLines(selectedFile.toPath());

                // Limpiar y convertir el contenido a formato CSV
                List<String> cleanedLines = cleanFileContentFromTxtForRespuestas(lines);

                if (cleanedLines.isEmpty()) {
                    showErrorAlert("Limpieza Eliminó Todo",
                            "El contenido del archivo TXT no es válido o fue eliminado durante la limpieza.");
                    return;
                }

                // Validar y escribir contenido limpio en el archivo CSV
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentRespuestasFile))) {
                    for (int i = 0; i < cleanedLines.size(); i++) {
                        String line = cleanedLines.get(i);
                        if (i == cleanedLines.size() - 1 && line.trim().isEmpty()) {
                            continue;
                        }
                        writer.write(line);
                        if (i < cleanedLines.size() - 1) {
                            writer.newLine();
                        }
                    }
                }

                System.out.println(
                        "Archivo de respuestas actualizado desde TXT: " + currentRespuestasFile.getAbsolutePath());

                // Mostrar alerta de éxito
                showSuccessAlert("Archivo de Respuestas Actualizado",
                        "El archivo de respuestas se ha actualizado correctamente desde el archivo TXT.");
            }
        } catch (IOException e) {
            showErrorAlert("Error al Actualizar Respuestas",
                    "Ocurrió un error al intentar actualizar el archivo de respuestas.");
        }
    }

    private List<String> cleanFileContentFromTxtForRespuestas(List<String> lines) {
        List<String> cleanedLines = new ArrayList<>();
        for (String line : lines) {
            String originalLine = line; // Guardar la línea original
            // Limpieza de caracteres no deseados
            line = line.replace("\uFEFF", "").replaceAll("[^\\p{ASCII}]", "");

            // Asegurarse de que la línea siga el formato esperado
            if (!line.trim().isEmpty() && isValidRespuestaFormat(line)) {
                cleanedLines.add(line);
            } else {
                System.out.println("Línea eliminada: " + originalLine + " -> " + line); // Agregar depuración
            }
        }

        if (cleanedLines.isEmpty()) {
            showErrorAlert("Limpieza Eliminó Todo",
                    "El contenido del archivo TXT no es válido o fue eliminado durante la limpieza.");
        }
        return cleanedLines;
    }

    private boolean isValidRespuestaFormat(String line) {
        // Ejemplo: verifica que la línea tenga al menos 5 campos separados por comas
        return line.matches("^[^,]+(,(si|no))+$"); // Asegúrate de que esta expresión regular sea correcta para el
                                                   // formato que esperas
    }

    private void showSuccessAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static List<String> cleanFileContent(File file) throws IOException {
        List<String> cleanedLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Eliminar BOM
                line = line.replace("\uFEFF", "");

                // Eliminar cualquier punto y coma o coma adicional al final de cada línea
                line = line.replaceAll("[;,]+\\s*$", "");
                // linea no vacia despues de limpieza
                if (!line.trim().isEmpty()) {
                    cleanedLines.add(line);
                }
            }
        }
        return cleanedLines;
    }

}
