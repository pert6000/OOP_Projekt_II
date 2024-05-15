package org.example.projekt;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage peaLava) throws IOException {
        Group juur = new Group();

        Scene stseen1 = new Scene(juur, 500, 500);

        VBox vb = addVBox();
        juur.getChildren().add(vb);
        juur.requestFocus();

        stseen1.widthProperty().addListener((observable, oldValue, newValue) -> {
            vb.setLayoutX((newValue.doubleValue() - vb.getWidth()) / 2);
        });

        peaLava.setTitle("Must ruut");
        peaLava.setScene(stseen1);
        peaLava.show();
        peaLava.setMinWidth(250);
        peaLava.setMinHeight(200);

        vb.setLayoutX(250 - vb.getWidth()/2);

    }

    public VBox addVBox() {
        VBox vbox = new VBox();

        vbox.setPadding(new Insets(10));
        vbox.setSpacing(14);
        vbox.setAlignment(Pos.CENTER);

        Text title = new Text("Tere tulemast!");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Text kirjeldus = new Text("Siin programmis saad andmeid analüüsida.");
        Button b1 = new Button("Analüüsima!");
        TextField path = new TextField();
        path.setMaxWidth(200);
        path.setPromptText("andmefaili asukoht");


        b1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String s = path.getText();
                System.out.println(s);
            }
        });

        vbox.getChildren().addAll(title,kirjeldus,path,b1);

        return vbox;
    }

    public static void main(String[] args) {
        launch();
    }
}