package com.kostlink.ui.view.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.kostlink.ui.navigation.SceneManager;
import com.kostlink.ui.view.auth.LoginView;
import com.kostlink.ui.view.auth.OwnerRegisterView;

public class WelcomeView {

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private WelcomeView() {
    }

    public static Scene createScene() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("""
                -fx-background-color: linear-gradient(to bottom right, #F8FAFC, #E2E8F0);
                """);

        Label title = new Label("KostLink");
        title.setStyle("""
                -fx-font-size: 42px;
                -fx-font-weight: bold;
                -fx-text-fill: #0F172A;
                """);

        Label subtitle = new Label("Platform manajemen kost modern untuk pemilik dan penyewa.");
        subtitle.setStyle("""
                -fx-font-size: 18px;
                -fx-text-fill: #475569;
                """);

        Button loginButton = new Button("Masuk");
        loginButton.setPrefWidth(280);
        loginButton.setPrefHeight(50);
        loginButton.setStyle("""
                -fx-background-color: #2563EB;
                -fx-text-fill: white;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-background-radius: 10;
                """);

        loginButton.setOnAction(e -> {
            SceneManager.switchScene(
                    LoginView.createScene(),
                    "Login"
            );
        });

        Button registerOwnerButton = new Button("Daftar Sebagai Pemilik");
        registerOwnerButton.setPrefWidth(280);
        registerOwnerButton.setPrefHeight(50);
        registerOwnerButton.setStyle("""
                -fx-background-color: #FFFFFF;
                -fx-text-fill: #1E293B;
                -fx-font-size: 16px;
                -fx-font-weight: bold;
                -fx-border-color: #CBD5E1;
                -fx-border-width: 1.5;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
                """);

        registerOwnerButton.setOnAction(e -> {
            SceneManager.switchScene(
                    OwnerRegisterView.createScene(),
                    "Registrasi Pemilik"
            );
        });

        root.getChildren().addAll(
                title,
                subtitle,
                loginButton,
                registerOwnerButton
        );

        return new Scene(root, WIDTH, HEIGHT);
    }
}