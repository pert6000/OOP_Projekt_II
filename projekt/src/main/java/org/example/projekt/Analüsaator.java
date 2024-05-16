package org.example.projekt;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projekt.stat_näitajad.statistilineNäitaja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.example.projekt.stat_näitajad.Main.*;

public class Analüsaator extends Application {

    private HashMap<String, statistilineNäitaja> väärtused;
    String fail;

    @Override
    public void start(Stage peaLava) throws IOException {
        Group juurStart = new Group();
        Group juurMenüü = new Group();
        Group juurNäitajad = new Group();

        Scene stseenStart = new Scene(juurStart, 700, 500);
        Scene stseenMenüü = new Scene(juurMenüü, 700, 500);
        Scene stseenNäitajad = new Scene(juurNäitajad, 700, 500);

        VBox vboxStart = new VBox();

        vboxStart.setPadding(new Insets(10));
        vboxStart.setSpacing(14);
        vboxStart.setAlignment(Pos.CENTER);

        //vbox stardimenüü jaoks
        Text title = new Text("Tere tulemast!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Text kirjeldus = new Text("Siin programmis saad andmeid analüüsida.");
        Button b1 = new Button("Analüüsima!");
        CheckBox päisega = new CheckBox("Andmetel on päised");
        TextField path = new TextField();

        HBox eraldajaKast = new HBox();
        eraldajaKast.setSpacing(10);

        Text eraldajaTekst = new Text("Andmete eraldaja:");
        TextField eraldaja = new TextField();
        path.setMaxWidth(200);
        path.setPromptText("andmefaili asukoht");
        eraldaja.setMaxSize(30,30);

        //vbox nuppude jaoks
        VBox vboxMenüü = new VBox();
        vboxMenüü.setPadding(new Insets(55, 0, 0, 0));
        vboxMenüü.setSpacing(25);
        vboxMenüü.setAlignment(Pos.CENTER);

        Button näitajadNupp = new Button("Statitiliste näitajate arvutus");
        Button histoNupp = new Button("Histogramm");
        Button scatNupp = new Button("Scatter");

        VBox vboxNäitajad = new VBox();
        vboxNäitajad.setPadding(new Insets(30, 0, 0, 0));
        vboxNäitajad.setSpacing(14);
        vboxNäitajad.setAlignment(Pos.CENTER);

        Button kirjutaNäitajadFaili = new Button("Kirjuta faili");
        kirjutaNäitajadFaili.setOnAction(event -> kirjutaFaili(väärtused,fail));

        Text näitajad = new Text();

        //nupuvajutuse handler stardimenüüs
        b1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                fail = path.getText();
                HashMap<String, ArrayList<Double>> arvud = new HashMap<>();

                try {
                    arvud = failistLugemine(fail, päisega.isSelected(), eraldaja.getText());
                    peaLava.setScene(stseenMenüü);
                    vboxMenüü.setLayoutX(350 - vboxMenüü.getWidth() / 2);
                } catch (IOException e) {
                    path.clear();
                    path.setPromptText(e.getMessage());
                    juurStart.requestFocus();
                }
            }
        });

        //juhul kui vajutatakse enterit, siis võtta seda, kui nupuvajutust
        stseenStart.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Trigger the action associated with the enterButton
                b1.fire();
            }
        });

        näitajadNupp.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                peaLava.setScene(stseenNäitajad);
                näitajad.setText(selgitaKõik(väärtused));
                vboxNäitajad.setLayoutX(350 - vboxNäitajad.getWidth() / 2);
            }
        });

        //teeme nii, et soovitud vboxid paigutuksid alati ekraani keskel
        stseenStart.widthProperty().addListener((observable, oldValue, newValue) -> {
            vboxStart.setLayoutX((newValue.doubleValue() - vboxStart.getWidth()) / 2);
        });

        stseenMenüü.widthProperty().addListener((observable, oldValue, newValue) -> {
            vboxMenüü.setLayoutX((newValue.doubleValue() - vboxMenüü.getWidth()) / 2);
        });

        stseenNäitajad.widthProperty().addListener((observable, oldValue, newValue) -> {
            vboxNäitajad.setLayoutX((newValue.doubleValue() - vboxNäitajad.getWidth()) / 2);
        });

        eraldaja.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 1) {
                eraldaja.setText(oldValue);
            }
        });

        //tekitame nupu, millega saab edasi tagasi liikuda
        Button tagasiStart = new Button("Tagasi");
        tagasiStart.setLayoutY(15);
        tagasiStart.setLayoutX(15);
        tagasiStart.setOnAction(event -> peaLava.setScene(stseenStart));

        Button tagasiMenüüsse = new Button("Tagasi");
        tagasiMenüüsse.setLayoutY(15);
        tagasiMenüüsse.setLayoutX(15);
        tagasiMenüüsse.setOnAction(event -> peaLava.setScene(stseenMenüü));


        juurMenüü.getChildren().add(tagasiStart);
        juurNäitajad.getChildren().add(tagasiMenüüsse);

        eraldajaKast.getChildren().addAll(eraldajaTekst, eraldaja);
        vboxStart.getChildren().addAll(title, kirjeldus, path,eraldajaKast, päisega, b1);
        vboxMenüü.getChildren().addAll(näitajadNupp, histoNupp, scatNupp);
        vboxNäitajad.getChildren().addAll(näitajad, kirjutaNäitajadFaili);

        juurStart.getChildren().add(vboxStart);
        juurMenüü.getChildren().add(vboxMenüü);
        juurNäitajad.getChildren().add(vboxNäitajad);

        juurStart.requestFocus();

        peaLava.setTitle("Valge ruut");
        peaLava.setScene(stseenStart);
        peaLava.show();
        peaLava.setMinWidth(250);
        peaLava.setMinHeight(225);

        vboxStart.setLayoutX(stseenStart.getWidth() / 2 - vboxStart.getWidth() / 2);

    }

    public static void main(String[] args) {
        launch();
    }
}