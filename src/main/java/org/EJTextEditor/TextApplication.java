package org.EJTextEditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

public class TextApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        WebView webView = new WebView();
        File htmlFile = new File("src/main/resources/editor.html");
        webView.getEngine().load(htmlFile.toURI().toString());
//        Text text = new Text(10, 40, "Hello World! My name is Erwyn");
//        text.setFont(new Font(40));
//        Scene scene = new Scene(new Group(text));
//
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();

        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.setTitle("EJ's Text Editor");
        //primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.show();
    }
}
