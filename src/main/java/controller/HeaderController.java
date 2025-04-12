package com.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class HeaderController {

    @FXML private ComboBox<String> themeCombo;
    @FXML private ComboBox<String> languageCombo;

    private Scene scene;
    private Stage primaryStage;
    private ResourceBundle bundle;

    public void initialize() {
        themeCombo.getItems().addAll("Light", "Dark");
        languageCombo.getItems().addAll("Türkçe", "English");

        themeCombo.setOnAction(e -> setTheme(themeCombo.getValue()));
        languageCombo.setOnAction(e -> setLanguage(languageCombo.getValue()));
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setTheme(String theme) {
        scene.getStylesheets().clear();
        if ("Dark".equals(theme) || "Koyu".equals(theme)) {
            scene.getStylesheets().add(getClass().getResource("/css/dark.css").toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource("/css/light.css").toExternalForm());
        }
    }

    private void setLanguage(String lang) {
        Locale locale = lang.equals("Türkçe") ? new Locale("tr") : new Locale("en");
        bundle = ResourceBundle.getBundle("bundles.lang", locale);
        updateTexts();
    }

    private void updateTexts() {
        themeCombo.setPromptText(bundle.getString("theme"));
        languageCombo.setPromptText(bundle.getString("language"));

        themeCombo.getItems().setAll(bundle.getString("light"), bundle.getString("dark"));
        languageCombo.getItems().setAll(bundle.getString("turkish"), bundle.getString("english"));
    }
}
