package com.cellwars.window;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Created by Tamás on 2015-04-13.
 */
public class Window {

    TextArea textArea;
    TextField commandlineField;
    Button executeButton;


    public void init(Stage primaryStage){

        textArea = new TextArea();
        textArea.setFont(Font.font("Consolas", 12));
        textArea.setStyle(
                "  -fx-text-fill: #039ED3;\n"
        );

        GridPane grid = new GridPane();
        grid.setVgap(4);

        grid.setAlignment(Pos.CENTER);

        grid.add(textArea, 0, 1);
        Scene scene = new Scene(grid, 640, 480);
        textArea.setPrefWidth(620);
        textArea.setPrefHeight(440);
        textArea.setEditable(false);

        commandlineField = new TextField();
        commandlineField.setPrefWidth(520);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);


        executeButton = new Button("Execute");
        executeButton.setPrefWidth(100);
        hbBtn.getChildren().add(commandlineField);
        hbBtn.getChildren().add(executeButton);
        grid.add(hbBtn, 0, 2);

        executeButton.setOnAction((ActionEvent event) -> {
            ;
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle("Cell Wars Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        commandlineField.requestFocus();
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public TextField getCommandlineField() {
        return commandlineField;
    }

    public Button getExecuteButton() {
        return executeButton;
    }


}
