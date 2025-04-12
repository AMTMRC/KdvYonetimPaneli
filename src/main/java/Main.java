package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        // Varsayılan dil Türkçe ayarlanıyor
        Locale locale = new Locale("tr");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.lang", locale);

        // Senin gerçek proje yapına göre burası "login.fxml" olmalı:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"), bundle);

        Scene scene = new Scene(loader.load());

        // Varsayılan tema (açık tema) uygulanıyor
        scene.getStylesheets().add(getClass().getResource("/css/light.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Yönetim Paneli");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
