package com.cellwars.server;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Tamás on 2015-04-30.
 */
public class CommandLine {

    private TextArea textArea;
    private TextField commandline;

    public CommandLine(String version, TextArea textArea, TextField commandline) {
        this.textArea = textArea;
        this.commandline = commandline;

        textArea.appendText("*********************************************************************************************\n");
        textArea.appendText("*                                     Cell Wars Server                                      *\n");
        textArea.appendText("*                                        Version " + version + "                                        *\n");
        textArea.appendText("*********************************************************************************************\n");
        textArea.appendText("\n");
    }

    public void print(String message) {
        textArea.appendText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm ")) + message + "\n");
    }

    public void execute(Server server) {
        print(commandline.getText());
        interpreter(commandline.getText(), server);
        commandline.setText("");
    }

    private void interpreter(String rawcommand, Server server) {
        String [] command = rawcommand.split("\\s+");
        switch (command[0]) {
            case "exit":
                server.stop();
                break;
        }
    }
}
