package com.cellwars;

import com.cellwars.server.CommandLine;
import com.cellwars.server.Server;
import com.cellwars.window.Window;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    String version = "1.0";
    CommandLine commandLine;
    Server server;


    @Override
    public void start(Stage primaryStage){

        // Init windows
        Window serverWindow = new Window();
        serverWindow.init(primaryStage);

        // Start server
        commandLine = new CommandLine(version, serverWindow.getTextArea(), serverWindow.getCommandlineField());
        server = new Server(commandLine);
        server.start();

        //Evenets
        serverWindow.getExecuteButton().setOnAction((ActionEvent event) -> {
            commandLine.execute(server);
        });
        serverWindow.getCommandlineField().setOnKeyPressed((KeyEvent key) -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                commandLine.execute(server);
            }
        });
        primaryStage.setOnCloseRequest((WindowEvent event) -> server.stop()
        );
    }


    public static void main(String[] args) {
        launch(args);
    }
}
