package org.example.projekt;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projekt.stat_näitajad.Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Analüsaator extends Application {

    @Override
    public void start(Stage peaLava) {
        Group juurStart = new Group();
        Group juurMenüü = new Group();

        Scene stseenStart = new Scene(juurStart, 500, 500);
        Scene stseenMenüü = new Scene(juurMenüü, 500, 500);

        VBox vboxStart = new VBox();
        vboxStart.setPadding(new Insets(10));
        vboxStart.setSpacing(14);
        vboxStart.setAlignment(Pos.CENTER);

        // VBox stardimenüü jaoks
        Text title = new Text("Tere tulemast!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Text kirjeldus = new Text("Siin programmis saad andmeid analüüsida.");
        Button b1 = new Button("Analüüsima!");
        TextField path = new TextField();
        path.setMaxWidth(200);
        path.setPromptText("andmefaili asukoht");

        // VBox nuppude jaoks
        VBox vboxMenüü = new VBox();
        vboxMenüü.setPadding(new Insets(55, 0, 0, 0));
        vboxMenüü.setSpacing(25);
        vboxMenüü.setAlignment(Pos.CENTER);

        Button näitajadNupp = new Button("Statitiliste näitajate arvutus");
        Button histoNupp = new Button("Histogramm");
        Button scatNupp = new Button("Scatter");

        // Kutsume välja meetodi histogrammi loomiseks
        histoNupp.setOnAction(event -> {
            String fail = path.getText();
            looHistogramm(peaLava, stseenStart, fail);
        });

        // Nupuvajutuse handler stardimenüüs
        b1.setOnAction(event -> {
            String fail = path.getText();
            double[] arvud = new double[0];
            try {
                // Kui vajutatakse "Analüüsima!" nuppu, siis kutsutakse esile meetod "looHistogramm"
                arvud = Main.failistLugemine(fail);
                looHistogramm(peaLava, stseenStart, fail);
                peaLava.setScene(stseenMenüü);
                vboxMenüü.setLayoutX(250 - vboxMenüü.getWidth() / 2);
            } catch (FileNotFoundException e) {
                // Kui faili ei leita, kuvatakse vastav teade
                path.clear();
                path.setPromptText("Faili ei leitud!");
                juurStart.requestFocus();
            }
        });

        // Juhul kui vajutatakse Enter klahvi, siis käivitatakse "Analüüsima!" nupp
        stseenStart.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                b1.fire();
            }
        });

        // Lisame elemendid VBoxidesse
        vboxStart.getChildren().addAll(title, kirjeldus, path, b1);
        vboxMenüü.getChildren().addAll(näitajadNupp, histoNupp, scatNupp);
        juurStart.getChildren().add(vboxStart);
        juurMenüü.getChildren().add(vboxMenüü);
        juurStart.requestFocus();

        // Stseenide suuruste muutmisel liigutatakse VBoxid ekraani keskele
        stseenStart.widthProperty().addListener((observable, oldValue, newValue) -> {
            vboxStart.setLayoutX((newValue.doubleValue() - vboxStart.getWidth()) / 2);
        });

        stseenMenüü.widthProperty().addListener((observable, oldValue, newValue) -> {
            vboxMenüü.setLayoutX((newValue.doubleValue() - vboxMenüü.getWidth()) / 2);
        });

        // Lisame "Tagasi" nupu
        Button tagasi = new Button("Tagasi");
        tagasi.setLayoutY(15);
        tagasi.setLayoutX(15);
        tagasi.setOnAction(event -> peaLava.setScene(stseenStart));
        juurMenüü.getChildren().add(tagasi);

        peaLava.setTitle("Valge ruut");
        peaLava.setScene(stseenStart);
        peaLava.show();
        peaLava.setMinWidth(250);
        peaLava.setMinHeight(225);

        vboxStart.setLayoutX(stseenStart.getWidth() / 2 - vboxStart.getWidth() / 2);
    }

    // Meetod histogrammi loomiseks
    private void looHistogramm(Stage peaLava, Scene praeguneStseen, String fail) {
        // Loome veerud
        int[] kümnendid = new int[11];

        //Loeb aastaid failist ning loeb kümnenditesse
        try (BufferedReader reader = new BufferedReader(new FileReader(fail))) {
            String rida;
            while ((rida = reader.readLine()) != null) {
                
                int year = Integer.parseInt(rida.trim());

                // Leiab kümnendi
                int decade = (year / 10) * 10;

                // Suurendab vastava kümnendi loendust
                int kümnendiIndeks = (decade - 1900) / 10;
                if (kümnendiIndeks >= 0 && kümnendiIndeks < kümnendid.length) {
                    kümnendid[kümnendiIndeks]++;
                } else {
                    // saab midagi siia panna
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        // Loob histogrammi
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> histogramm = new BarChart<>(xAxis, yAxis);
        xAxis.setLabel("Kümnend");
        yAxis.setLabel("Sagedus");

        // Lisab andmed histogrammi
        XYChart.Series<String, Number> andmeSeeria = new XYChart.Series<>();
        for (int i = 0; i < kümnendid.length; i++) {
            
            String silt = String.format("%ds", 1900 + i * 10);

            andmeSeeria.getData().add(new XYChart.Data<>(silt, kümnendid[i]));
        }
        histogramm.getData().add(andmeSeeria);

        // Preagune stseen
        Scene eelmineStseen = praeguneStseen;

        // Tagasinupp
        VBox vbox = new VBox();
        Button tagasiNupp = new Button("Tagasi");
        tagasiNupp.setOnAction(event -> peaLava.setScene(eelmineStseen));
        vbox.getChildren().addAll(histogramm, tagasiNupp);

        // Uus stseen
        peaLava.setScene(new Scene(vbox));
    }


    public static void main(String[] args) {
        launch();
    }
}
