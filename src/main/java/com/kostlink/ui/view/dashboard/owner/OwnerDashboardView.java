package com.kostlink.ui.view.dashboard.owner;

import com.kostlink.ui.app.session.AppSession;
import com.kostlink.ui.navigation.SceneManager;
import com.kostlink.ui.view.auth.WelcomeView;
import com.kostlink.ui.view.settings.SettingsView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OwnerDashboardView {

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private OwnerDashboardView() {}

    public static Scene createScene() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FAFC;");

        VBox sidebar = createSidebar();
        HBox topbar = createTopbar();
        VBox content = createContent();

        root.setLeft(sidebar);
        root.setTop(topbar);
        root.setCenter(content);

        return new Scene(root, WIDTH, HEIGHT);
    }

    // =========================================================
    // SIDEBAR
    // =========================================================
    private static VBox createSidebar() {

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #1E293B;");

        Label title = new Label("KostLink");
        title.setStyle("""
                -fx-text-fill: white;
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                """);

        Button berandaBtn = new Button("Beranda");
        Button kelolaKamarBtn = new Button("Kelola Kamar");
        Button penghuniBtn = new Button("Data Penghuni");
        Button pembayaranBtn = new Button("Pembayaran");
        Button keluhanBtn = new Button("Keluhan");

        styleSidebarButton(berandaBtn);
        styleSidebarButton(kelolaKamarBtn);
        styleSidebarButton(penghuniBtn);
        styleSidebarButton(pembayaranBtn);
        styleSidebarButton(keluhanBtn);

        sidebar.getChildren().addAll(
                title,
                berandaBtn,
                kelolaKamarBtn,
                penghuniBtn,
                pembayaranBtn,
                keluhanBtn
        );

        return sidebar;
    }

    private static void styleSidebarButton(Button btn) {
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("""
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-alignment: center-left;
                -fx-font-size: 14px;
                """);
    }

    // =========================================================
    // TOPBAR
    // =========================================================
    private static HBox createTopbar() {

        HBox topbar = new HBox(20);
        topbar.setPadding(new Insets(15));
        topbar.setAlignment(Pos.CENTER_LEFT);
        topbar.setStyle("""
                -fx-background-color: white;
                -fx-border-color: #E2E8F0;
                -fx-border-width: 0 0 1 0;
                """);

        Label kostInfo = new Label("Kost ID: " + AppSession.getKostId());
        Label userInfo = new Label("User: " + AppSession.getUsername());

        Button settingsBtn = new Button("Pengaturan");
        Button logoutBtn = new Button("Logout");

        // NAVIGATE TO SETTINGS
        settingsBtn.setOnAction(e -> {
            SceneManager.switchScene(
                    SettingsView.createScene(),
                    "Pengaturan"
            );
        });

        logoutBtn.setOnAction(e -> {
            AppSession.clear();
            SceneManager.switchScene(
                    WelcomeView.createScene(),
                    "KostLink"
            );
        });

        topbar.getChildren().addAll(kostInfo, userInfo, settingsBtn, logoutBtn);

        return topbar;
    }

    // =========================================================
    // CONTENT (BERANDA)
    // =========================================================
    private static VBox createContent() {

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        Label status = new Label(
                AppSession.isOnboardingCompleted()
                        ? "Status: Data kos lengkap"
                        : "Status: Data kos belum lengkap"
        );

        HBox stats = new HBox(15);

        stats.getChildren().addAll(
                createStatCard("Total Kamar", "0"),
                createStatCard("Total Penghuni", "0"),
                createStatCard("Kamar Tersedia", "0")
        );

        content.getChildren().addAll(status, stats);

        // =====================================================
        // WARNING BOX (ONLY IF ONBOARDING NOT COMPLETED)
        // =====================================================
        if (!AppSession.isOnboardingCompleted()) {

            VBox warningBox = new VBox(10);
            warningBox.setPadding(new Insets(15));
            warningBox.setStyle("""
                    -fx-background-color: #FEF3C7;
                    -fx-background-radius: 10;
                    """);

            Label warningText = new Label(
                    "Silakan lengkapi data pemilik dan data kos untuk mulai menggunakan sistem."
            );

            Button setupBtn = new Button("Lengkapi Data");

            setupBtn.setOnAction(e -> {
                SceneManager.switchScene(
                        SettingsView.createScene(),
                        "Pengaturan"
                );
            });

            warningBox.getChildren().addAll(warningText, setupBtn);

            content.getChildren().add(warningBox);
        }

        return content;
    }

    // =========================================================
    // STAT CARD
    // =========================================================
    private static VBox createStatCard(String title, String value) {

        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setStyle("""
                -fx-background-color: white;
                -fx-background-radius: 10;
                -fx-border-color: #E2E8F0;
                -fx-border-radius: 10;
                """);

        Label t = new Label(title);
        Label v = new Label(value);

        t.setStyle("-fx-text-fill: #64748B;");
        v.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        card.getChildren().addAll(t, v);

        return card;
    }
}