package com.kostlink.ui.view.settings;

import com.kostlink.ui.app.session.AppSession;
import com.kostlink.ui.navigation.SceneManager;
import com.kostlink.ui.view.dashboard.owner.OwnerDashboardView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SettingsView {

    private static final double WIDTH = 1280;
    private static final double HEIGHT = 720;

    private SettingsView() {}

    public static Scene createScene() {

        VBox root = new VBox(16);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F8FAFC;");

        // =========================
        // TITLE
        // =========================
        Label title = new Label("Pengaturan");
        title.setStyle("""
                -fx-font-size: 26px;
                -fx-font-weight: bold;
                -fx-text-fill: #0F172A;
                """);

        // =========================
        // ACCOUNT INFO (READ ONLY)
        // =========================
        Label infoTitle = new Label("Informasi Akun");
        infoTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label usernameLabel = new Label("Username: " + AppSession.getUsername());
        Label kostIdLabel = new Label("Kost ID: " + AppSession.getKostId());

        // =========================
        // OWNER & KOS DATA (CORE)
        // =========================
        Label coreTitle = new Label("Data Pemilik & Kos");
        coreTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label namaPemilikLabel = new Label("Nama Pemilik:");
        TextField namaPemilikField = new TextField();
        namaPemilikField.setPromptText("Masukkan nama pemilik");

        Label noTelpLabel = new Label("Nomor Telepon Pemilik:");
        TextField noTelpField = new TextField();
        noTelpField.setPromptText("08xx / +62xx");

        Label namaKosLabel = new Label("Nama Kos:");
        TextField namaKosField = new TextField();

        Label alamatKosLabel = new Label("Alamat Kos:");
        TextField alamatKosField = new TextField();

        Label kontakPengelolaLabel = new Label("Nomor Pengelola (opsional):");
        TextField kontakPengelolaField = new TextField();

        // =========================
        // REKENING (OPTIONAL)
        // =========================
        Label rekeningTitle = new Label("Data Rekening (Opsional)");
        rekeningTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label bankLabel = new Label("Nama Bank:");
        TextField bankField = new TextField();

        Label namaRekLabel = new Label("Nama Pemilik Rekening:");
        TextField namaRekField = new TextField();

        Label noRekLabel = new Label("Nomor Rekening:");
        TextField noRekField = new TextField();

        // =========================
        // BUTTONS
        // =========================
        Button saveBtn = new Button("Simpan");
        saveBtn.setPrefWidth(200);
        saveBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: white;");

        Button deleteBtn = new Button("Hapus Akun");
        deleteBtn.setPrefWidth(200);
        deleteBtn.setStyle("-fx-background-color: #DC2626; -fx-text-fill: white;");

        Button backBtn = new Button("Kembali");

        // =========================
        // ACTIONS
        // =========================
        saveBtn.setOnAction(e -> {
            AppSession.setOnboardingCompleted(true);
            SceneManager.switchScene(
                    OwnerDashboardView.createScene(),
                    "Dashboard Owner"
            );
        });

        deleteBtn.setOnAction(e -> {
            System.out.println("DELETE ACCOUNT (TODO)");
        });

        backBtn.setOnAction(e -> {
            SceneManager.switchScene(
                    OwnerDashboardView.createScene(),
                    "Dashboard Owner"
            );
        });

        // =========================
        // LAYOUT
        // =========================
        root.getChildren().addAll(
                title,
                new Separator(),

                infoTitle,
                usernameLabel,
                kostIdLabel,

                new Separator(),

                coreTitle,
                namaPemilikLabel,
                namaPemilikField,
                noTelpLabel,
                noTelpField,
                namaKosLabel,
                namaKosField,
                alamatKosLabel,
                alamatKosField,
                kontakPengelolaLabel,
                kontakPengelolaField,

                new Separator(),

                rekeningTitle,
                bankLabel,
                bankField,
                namaRekLabel,
                namaRekField,
                noRekLabel,
                noRekField,

                new Separator(),

                saveBtn,
                deleteBtn,
                backBtn
        );

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #F8FAFC; -fx-border-color: transparent;");

        return new Scene(scrollPane, WIDTH, HEIGHT);
    }
}