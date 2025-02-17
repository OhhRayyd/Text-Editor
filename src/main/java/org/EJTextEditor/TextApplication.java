package org.EJTextEditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextApplication extends Application {

    private WebEngine webEngine;
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();

        File htmlFile = new File("src/main/resources/editor.html");
        webView.getEngine().load(htmlFile.toURI().toString());
        webEngine = webView.getEngine();
//        Text text = new Text(10, 40, "Hello World! My name is Erwyn");
//        text.setFont(new Font(40));
//        Scene scene = new Scene(new Group(text));
//
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
        webEngine.setOnAlert(event -> System.out.println("JS Alert: " + event.getData()));
        webEngine.documentProperty().addListener((observable, oldValue, newDoc) -> {
            if (newDoc != null) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", this);
            }
        });

        primaryStage.setScene(new Scene(webView, 800, 725));
        primaryStage.setTitle("EJ's Text Editor");
        //primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }

    public void javafxSaveFile(String content) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        String userHome = System.getProperty("user.home");
        File documentsDir = new File(userHome, "Documents");
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        File defaultFile = new File(documentsDir, "SavedEJText_" + timestamp + ".txt");


        //Ensures the documents folder exists before setting it
        if(documentsDir.exists()) {
            fileChooser.setInitialDirectory(documentsDir);
            fileChooser.setInitialFileName(defaultFile.getName());
        }

        //Set file extension filter
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void javafxLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");

        // Set default folder to Documents
        File documentsDir = new File(System.getProperty("user.home"), "Documents");
        if (documentsDir.exists()) {
            fileChooser.setInitialDirectory(documentsDir);
        }

        // Allow only .txt files
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        // Show Open File dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n"); // Properly escape newlines for JavaScript
                }

                // Escape double quotes and backslashes to avoid breaking the JS script
                String jsCommand = getString(content);
                webEngine.executeScript(jsCommand);

                System.out.println("File loaded: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getString(StringBuilder content) {
        String safeContent = content.toString()
                .replace("\\", "\\\\")  // Escape backslashes
                .replace("\"", "\\\"")  // Escape double quotes
                .replace("\n","\\n");   // Convert real newlines to JS-friendly format


        // Execute JavaScript to update the editor content
        return "editor.setValue(\"" + safeContent + "\");";
    }
}
