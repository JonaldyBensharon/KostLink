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

public class LoginView {

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private LoginView() {}

    public static Scene createScene() {

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #F8FAFC;");

        Label title = new Label("Login KostLink");
        title.setStyle("""
                -fx-font-size: 26px;
                -fx-font-weight: bold;
                -fx-text-fill: #0F172A;
                """);

        TextField kostIdField = new TextField();
        kostIdField.setPromptText("Kost ID");
        kostIdField.setMaxWidth(300);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(300);
        loginButton.setPrefHeight(45);
        loginButton.setStyle("""
                -fx-background-color: #2563EB;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                """);

        loginButton.setOnAction(e -> {

            String kostId = kostIdField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            errorLabel.setText("");

            // =========================
            // VALIDATION INPUT
            // =========================
            if (kostId == null || kostId.isBlank()) {
                errorLabel.setText("Kost ID wajib diisi");
                return;
            }

            if (username == null || username.isBlank()) {
                errorLabel.setText("Username wajib diisi");
                return;
            }

            if (password == null || password.isBlank()) {
                errorLabel.setText("Password wajib diisi");
                return;
            }

            // =========================
            // MOCK AUTH VALIDATION
            // =========================
            boolean valid = AuthMockDatabase.validateLogin(
                    kostId,
                    username,
                    password
            );

            if (!valid) {
                errorLabel.setText("Login gagal: data tidak sesuai");
                return;
            }

            // =========================
            // INIT SESSION
            // =========================
            AppSession.startSession(
                    kostId,
                    username,
                    AppSession.Role.OWNER
            );

            // =========================
            // REDIRECT TO DASHBOARD
            // =========================
            SceneManager.switchScene(
                    OwnerDashboardView.createScene(),
                    "Owner Dashboard"
            );
        });

        root.getChildren().addAll(
                title,
                kostIdField,
                usernameField,
                passwordField,
                loginButton,
                errorLabel
        );

        return new Scene(root, WIDTH, HEIGHT);
    }
}