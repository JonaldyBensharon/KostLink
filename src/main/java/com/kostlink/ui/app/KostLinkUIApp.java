package com.kostlink.ui.app;

import com.kostlink.ui.navigation.SceneManager;
import com.kostlink.ui.view.auth.WelcomeView;
import javafx.application.Application;
import javafx.stage.Stage;

public class KostLinkUIApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager.setStage(primaryStage);
        SceneManager.switchScene(
                WelcomeView.createScene(),
                "KostLink"
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}