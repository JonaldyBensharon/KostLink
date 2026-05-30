package com.kostlink.ui.view.auth;

import com.kostlink.ui.app.session.AppSession;
import com.kostlink.ui.auth.mock.AuthMockDatabase;
import com.kostlink.ui.navigation.SceneManager;
import com.kostlink.ui.view.dashboard.owner.OwnerDashboardView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class OwnerRegisterView {

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private OwnerRegisterView() {}

    public static Scene createScene() {

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("""
                -fx-background-color: linear-gradient(to bottom right, #F8FAFC, #E2E8F0);
                """);

        Label title = new Label("Registrasi Pemilik");
        title.setStyle("""
                -fx-font-size: 28px;
                -fx-font-weight: bold;
                -fx-text-fill: #0F172A;
                """);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Konfirmasi Password");
        confirmPasswordField.setMaxWidth(300);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button registerButton = new Button("Daftar");
        registerButton.setPrefWidth(300);
        registerButton.setPrefHeight(45);
        registerButton.setStyle("""
                -fx-background-color: #2563EB;
                -fx-text-fill: white;
                -fx-font-size: 14px;
                -fx-font-weight: bold;
                -fx-background-radius: 10;
                """);

        Button backButton = new Button("Kembali");
        backButton.setPrefWidth(300);
        backButton.setPrefHeight(45);
        backButton.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: #1E293B;
                -fx-border-color: #CBD5E1;
                -fx-border-radius: 10;
                """);

        // =========================
        // REGISTER FLOW (FINAL)
        // =========================
        registerButton.setOnAction(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirm = confirmPasswordField.getText();

            errorLabel.setText("");

            if (username == null || username.isBlank()) {
                errorLabel.setText("Username tidak boleh kosong");
                return;
            }

            if (password == null || password.isBlank()) {
                errorLabel.setText("Password tidak boleh kosong");
                return;
            }

            if (!password.equals(confirm)) {
                errorLabel.setText("Password tidak cocok");
                return;
            }

            // =========================
            // GENERATE KOST ID
            // =========================
            String kostId = generateKostId();

            // =========================
            // STORE TO MOCK DATABASE
            // =========================
            AuthMockDatabase.registerOwner(kostId, username, password);

            // =========================
            // INIT SESSION
            // =========================
            AppSession.startSession(
                    kostId,
                    username,
                    AppSession.Role.OWNER
            );

            // =========================
            // SHOW INFO KOST ID
            // =========================
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrasi Berhasil");
            alert.setHeaderText("Kost ID Anda");
            alert.setContentText(
                    "Akun berhasil dibuat!\n\n" +
                            "KOST ID: " + kostId + "\n\n" +
                            "Simpan ID ini untuk login."
            );
            alert.showAndWait();

            // =========================
            // GO TO DASHBOARD
            // =========================
            SceneManager.switchScene(
                    OwnerDashboardView.createScene(),
                    "Owner Dashboard"
            );
        });

        backButton.setOnAction(e -> {
            SceneManager.switchScene(
                    WelcomeView.createScene(),
                    "KostLink"
            );
        });

        root.getChildren().addAll(
                title,
                usernameField,
                passwordField,
                confirmPasswordField,
                registerButton,
                backButton,
                errorLabel
        );

        return new Scene(root, WIDTH, HEIGHT);
    }

    private static String generateKostId() {
        return "KST-" + UUID.randomUUID().toString()
                .substring(0, 8)
                .toUpperCase();
    }
}