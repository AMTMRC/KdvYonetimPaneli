package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MainPanelController {

    @FXML private AnchorPane contentArea;
    @FXML private Label clockLabel;
    @FXML private Region spacer;

    @FXML
    public void initialize() {
        startClock();
        loadUserPanel(); // ilk açıldığında kullanıcı paneli yüklensin
    }

    private void loadPanel(String fxmlPath) {
        try {
            Node node = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(node);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadUserPanel() {
        loadPanel("/view/admin.fxml");
    }

    @FXML
    private void loadKdvPanel() {
        loadPanel("/view/kdv.fxml");
    }

    @FXML
    private void loadNotesPanel() {
        loadPanel("/view/notes.fxml");
    }

    @FXML
    private void loadProfilePanel() {
        loadPanel("/view/profile.fxml");
    }

    @FXML
    private void loadSettingsPanel() {
        loadPanel("/view/settings.fxml");
    }

    @FXML
    private void loadBackupPanel() {
        loadPanel("/view/backup.fxml");
    }

    @FXML
    private void loadNotificationsPanel() {
        loadPanel("/view/notifications.fxml");
    }

    @FXML
    private void toggleTheme() {
        System.out.println("Tema değiştirildi (örnek).");
        // tema değişimi burada yapılabilir
    }

    @FXML
    private void toggleLanguage() {
        System.out.println("Dil değiştirildi (örnek).");
        // dil değişimi burada yapılabilir
    }

    private void startClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            clockLabel.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
}
