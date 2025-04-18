package org.example.projekt;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projekt.stat_näitajad.statistilineNäitaja;

import java.io.IOException;
import java.util.*;

import static org.example.projekt.stat_näitajad.Main.*;

public class Analüsaator extends Application {

    private HashMap<String, statistilineNäitaja> väärtused;
    String fail;

    @Override
    public void start(Stage peaLava) {

        //loome juured
        Group juurStart = new Group();
        Group juurMenüü = new Group();
        Group juurNäitajad = new Group();

        //loome stseenid
        Scene stseenStart = new Scene(juurStart, 700, 500);
        Scene stseenMenüü = new Scene(juurMenüü, 700, 500);
        Scene stseenNäitajad = new Scene(juurNäitajad, 700, 500);

        //loome vboxid
        VBox vboxStart = new VBox();
        vboxStart.setPadding(new Insets(10));
        vboxStart.setSpacing(14);
        vboxStart.setAlignment(Pos.CENTER);

        // VBox stardimenüü jaoks
        Text title = new Text("Tere tulemast!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Text kirjeldus = new Text("Siin programmis saad andmeid analüüsida." + System.lineSeparator()+
                "Sisesta kasti andmefaili asukoht. Kui andmetes" + System.lineSeparator() +
                "on vaid üks tulp, siis jätta eraldaja lahter tühjaks.");
        Button b1 = new Button("Analüüsima!");
        CheckBox päisega = new CheckBox("Andmetel on päised");
        TextField path = new TextField();
        Text abimenüü = new Text("Kirjuta siia tulpade päised, mille andmeid " +
                "analüüsida." + System.lineSeparator() + "Juhul, kui päiseid pole, siis kasuta tulba numbrit. "+ System.lineSeparator() +"" +
                "Mitme tulba puhul erala tulbad koma abil.");

        HBox eraldajaKast = new HBox();
        eraldajaKast.setSpacing(10);

        Text eraldajaTekst = new Text("Andmete eraldaja:");
        TextField eraldaja = new TextField();
        Text kirjutatud = new Text("Andemed kirjutatud faili!");
        path.setMaxWidth(200);
        path.setPromptText("andmefaili asukoht");
        eraldaja.setMaxSize(30, 30);

        // VBox nuppude jaoks
        VBox vboxMenüü = new VBox();
        vboxMenüü.setPadding(new Insets(55, 0, 0, 0));
        vboxMenüü.setSpacing(25);
        vboxMenüü.setAlignment(Pos.CENTER);

        TextField analüüsitavadTulbad = new TextField();
        analüüsitavadTulbad.setPromptText("Analüüsitavad tulbad");
        Button näitajadNupp = new Button("Statitiliste näitajate arvutus");
        Button histoNupp = new Button("Histogramm");
        Button scatNupp = new Button("Scatter");

        VBox vboxNäitajad = new VBox();
        vboxNäitajad.setPadding(new Insets(30, 0, 0, 0));

        vboxNäitajad.setAlignment(Pos.CENTER);

        Button kirjutaNäitajadFaili = new Button("Kirjuta faili");


        Text näitajad = new Text();

        final String[] tulbad = new String[1];
        final HashMap<String, ArrayList<Double>>[] arvud = new HashMap[]{new HashMap<>()};

        //nupuvajutuse handler stardimenüüs
        b1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                fail = path.getText();

                try {
                    arvud[0] = failistLugemine(fail, päisega.isSelected(), eraldaja.getText());
                    System.out.println(arvud[0].keySet());
                    peaLava.setScene(stseenMenüü);
                    vboxMenüü.setLayoutX(350 - vboxMenüü.getWidth() / 2);
                } catch (RuntimeException | IOException e) {
                    path.clear();
                    path.setPromptText(e.getMessage());
                    juurStart.requestFocus();
                }
            }
        });
        // Kutsume välja meetodi histogrammi loomiseks
        histoNupp.setOnAction(event -> {
            tulbad[0] = analüüsitavadTulbad.getText();
            if (tulbad[0].contains(",")) {
                analüüsitavadTulbad.setText("");
                analüüsitavadTulbad.setPromptText("Ei saa teha mitme tulbaga.");
            } else {
                if (päisega.isSelected())
                    looHistogramm(peaLava, stseenStart, arvud[0], tulbad[0]);
                else {
                    String tulp = tulbad[0] + ". tulp";
                    looHistogramm(peaLava, stseenStart, arvud[0], tulp);
                }
            }
        });

        // Juhul kui vajutatakse Enter klahvi, siis käivitatakse "Analüüsima!" nupp
        stseenStart.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                b1.fire();
            }
        });

        // Stseenide suuruste muutmisel liigutatakse VBoxid ekraani keskele
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

        kirjutaNäitajadFaili.setOnAction(event -> {
            kirjutaFaili(väärtused, fail);
            vboxNäitajad.getChildren().add(kirjutatud);
        });

        //anname funktsiooni nupule, millega luuakse leht, kus stat_näitajaid
        näitajadNupp.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String tulp;
                tulbad[0] = analüüsitavadTulbad.getText(); //millist tulpa soovib kasutaja analüüsida
                if (tulbad[0].contains(",")) { //kas kasutaja on sisestanud mitu tulpa?
                    analüüsitavadTulbad.setText("");
                    analüüsitavadTulbad.setPromptText("Ei saa teha mitme tulbaga.");

                } else if (tulbad[0].isEmpty()) { //kas kasutaja on välja tühjaks jätnud?
                    analüüsitavadTulbad.setPromptText("Peab valima tulba");

                } else {
                    if (päisega.isSelected()) { //kas andmed on päistega
                        tulp = tulbad[0];

                    } else {
                        tulp = tulbad[0] + ". tulp";
                    }

                    väärtused = teeMap(ujukomaMassiiviks(arvud[0].get(tulp)));
                    peaLava.setScene(stseenNäitajad);
                    näitajad.setText(selgitaKõik(väärtused));
                    vboxNäitajad.setLayoutX(100);
                }
            }
        });

        //nupp, mille tagajärjel luuakse scatterchart
        scatNupp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tulbad[0] = analüüsitavadTulbad.getText();
                String[] soovitudTulbad = tulbad[0].split(",");
                if (soovitudTulbad.length != 2) { //kas on õige kogus soovitud tulpasid
                    analüüsitavadTulbad.setText("");
                    analüüsitavadTulbad.setPromptText("Tulpasid peab olema kaks.");
                } else {
                    String esimene;
                    String teine;
                    if (päisega.isSelected()) { //kas andmed on päistega
                        esimene = soovitudTulbad[0];
                        teine = soovitudTulbad[1];
                    } else { //kui ei ole päistega
                        esimene = soovitudTulbad[0] + ". tulp";
                        teine = soovitudTulbad[1] + ". tulp";
                    }

                    //loome graafiku
                    looScatter(peaLava, stseenMenüü, arvud[0], esimene, teine);
                }

            }
        });

        //tekitame nupud, millega saab edasi tagasi liikuda
        Button tagasiStart = new Button("Tagasi");
        tagasiStart.setLayoutY(15);
        tagasiStart.setLayoutX(15);
        tagasiStart.setOnAction(event -> peaLava.setScene(stseenStart));

        Button tagasiMenüüsse = new Button("Tagasi");
        tagasiMenüüsse.setLayoutY(15);
        tagasiMenüüsse.setLayoutX(15);
        tagasiMenüüsse.setOnAction(event -> peaLava.setScene(stseenMenüü));

        //paigutame objektid nii, et tekiks soovitud struktuur otspunktide ja juurte vahel
        juurMenüü.getChildren().add(tagasiStart);
        juurNäitajad.getChildren().add(tagasiMenüüsse);

        eraldajaKast.getChildren().addAll(eraldajaTekst, eraldaja);
        vboxStart.getChildren().addAll(title, kirjeldus, path, eraldajaKast, päisega, b1);
        vboxMenüü.getChildren().addAll(abimenüü,analüüsitavadTulbad, näitajadNupp, histoNupp, scatNupp);
        vboxNäitajad.getChildren().addAll(näitajad, kirjutaNäitajadFaili);

        juurStart.getChildren().add(vboxStart);
        juurMenüü.getChildren().add(vboxMenüü);
        juurNäitajad.getChildren().add(vboxNäitajad);

        juurStart.requestFocus();

        peaLava.setTitle("Analüsaator");
        peaLava.setScene(stseenStart);
        peaLava.show();
        peaLava.setMinWidth(250);
        peaLava.setMinHeight(225);

        vboxStart.setLayoutX(stseenStart.getWidth() / 2 - vboxStart.getWidth() / 2);

    }

    private void looScatter(Stage peaLava, Scene praeguneStseen, HashMap<String, ArrayList<Double>> arvud, String esimene, String teine) {
        //meetod, millega luuakse scatter chart etteantud andmestiku põhjal

        //eraldatakse mõlema dataseti andmed
        ArrayList<Double> esimeneArvud = arvud.get(esimene);
        ArrayList<Double> teineArvud = arvud.get(teine);

        //algatame teljed
        NumberAxis xAxis = new NumberAxis(Collections.min(esimeneArvud), Collections.max(esimeneArvud), 3);
        xAxis.setLabel(esimene);

        NumberAxis yAxis = new NumberAxis(Collections.min(teineArvud), Collections.max(teineArvud), 3);
        yAxis.setLabel(teine);

        ScatterChart<Double, Double> scatter = new ScatterChart(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();

        //lisame ettantud andmestiku scattercharti andmestikku
        for (int i = 0; i < esimeneArvud.size(); i++) {
            series.getData().add(new XYChart.Data(esimeneArvud.get(i), teineArvud.get(i)));
        }

        scatter.getData().addAll(series);

        //kujundame scatteri selliseks nagu soovime
        scatter.setLegendVisible(false);

        VBox vbox = new VBox();
        vbox.setSpacing(14);
        Button tagasiNupp = new Button("Tagasi");
        //CheckBox trendijoon = new CheckBox("Trendijoon");

        tagasiNupp.setOnAction(event -> peaLava.setScene(praeguneStseen));
        vbox.getChildren().addAll(scatter, tagasiNupp);

        peaLava.setScene(new Scene(vbox));

    }

    // Meetod histogrammi loomiseks
    private void looHistogramm(Stage peaLava, Scene praeguneStseen, HashMap<String, ArrayList<Double>> arvud, String tulbad) {

        ArrayList<Double> andmed = arvud.get(tulbad);

        // Algab kümnendite loendurite initsialiseerimisega
        double väikseimArv = Double.MAX_VALUE;
        double suurimArv = Double.MIN_VALUE;
        Map<Double, Double> LoenduridKaart = new TreeMap<>();

        for (Double d : andmed) {
            // Uuendab väikseimat ja suurimat aastat
            väikseimArv = Math.min(väikseimArv, d);
            suurimArv = Math.max(suurimArv, d);

            // Suurendab vastava vahemiku loenduri väärtust
            LoenduridKaart.put(d, LoenduridKaart.getOrDefault(d, 0.0) + 1);
        }
        for (Map.Entry<Double, Double> entry : LoenduridKaart.entrySet()) {
            System.out.print(entry.getKey());
            System.out.println(": " + entry.getValue());
        }

        // Küsib kasutajalt veergude arvu
        TextInputDialog dialoog = new TextInputDialog("10");
        dialoog.setTitle("Histogrammi Veerud");
        dialoog.setHeaderText("Sisesta histogrammi jaoks veergude arv:");
        dialoog.setContentText("Veerude arv:");

        Optional<String> tulemus = dialoog.showAndWait();
        if (tulemus.isPresent()) {
            int veergudeArv = Integer.parseInt(tulemus.get());

            // Arvutab iga veeru laiuse
            double veeruLaius = (suurimArv - väikseimArv + 1) / veergudeArv;

            // Loob histogrammi diagrammi
            CategoryAxis xTelg = new CategoryAxis();
            NumberAxis yTelg = new NumberAxis();
            BarChart<String, Number> histogramm = new BarChart<>(xTelg, yTelg);
            xTelg.setLabel("Vahemik");
            yTelg.setLabel("Sagedus");

            // Lisab andmed histogrammi diagrammile
            XYChart.Series<String, Number> andmeSeeria = new XYChart.Series<>();
            for (int i = 0; i < veergudeArv; i++) {
                // Määrab veeru algus- ja lõpu aasta
                double algusArv = väikseimArv + i * veeruLaius;
                double lõpuArv = algusArv + veeruLaius - 1;

                // Määrab veeru märgise
                String kümnendiMärgis = String.format("%d-%d", Math.round(algusArv), Math.round(lõpuArv));

                // Arvutab veeru kogusagedus
                int koguSagedus = 0;
                for (Double a : LoenduridKaart.keySet()) {
                    if (a > algusArv && a <= lõpuArv)
                        koguSagedus += LoenduridKaart.getOrDefault(a, 0.0);
                }
                System.out.println(koguSagedus);
                // Lisab veeru seeriasse
                andmeSeeria.getData().add(new XYChart.Data<>(kümnendiMärgis, koguSagedus));
            }
            histogramm.getData().add(andmeSeeria);

            // Salvestab praeguse stseeni

            // Lisab tagasivajumisnupu ja histogrammi uude stseeni
            VBox vbox = new VBox();
            Button tagasiNupp = new Button("Tagasi");
            vbox.setSpacing(14);
            tagasiNupp.setOnAction(event -> peaLava.setScene(praeguneStseen));
            histogramm.setLegendVisible(false);
            vbox.getChildren().addAll(histogramm, tagasiNupp);

            // Näitab uut stseeni
            peaLava.setScene(new Scene(vbox));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
