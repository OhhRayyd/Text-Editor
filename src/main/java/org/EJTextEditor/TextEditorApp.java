package org.EJTextEditor;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

public class TextEditorApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        File htmlFile = new File("src/main/resources/editor.html");
        webView.getEngine().load(htmlFile.toURI().toString());

        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.setTitle("Web-Based Text Editor");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
