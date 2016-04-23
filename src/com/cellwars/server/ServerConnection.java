package com.cellwars.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tam�s on 2015-04-27.
 */
public class ServerConnection {

    private ServerSocket serverSocket;
    private List<Socket> clients;
    private List<DataInputStream> in;
    private List<DataOutputStream> out;

    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(0);
        clients = new ArrayList<>();
        in = new ArrayList<>();
        out = new ArrayList<>();
    }

    public void waitForClient() throws IOException {
        try {
            Socket client = serverSocket.accept();
            clients.add(client);
        } catch (SocketException e) {
            return;
        }
    }


    public void connectionEstablished(int index) throws IOException {
        in.add(new DataInputStream(clients.get(index).getInputStream()));
        out.add(new DataOutputStream(clients.get(index).getOutputStream()));
    }

    public String getMessage(int index) throws IOException {
        try {
            return in.get(index).readUTF();
        } catch (SocketException e) {
            return null;
        }
    }

    public void sendMessage(int index, String ... message) throws IOException {
        try {
            String joinedMessage = Stream.of(message)
                    .collect(Collectors.joining(":"));

            out.get(index).writeUTF(joinedMessage);
        } catch (SocketException | NullPointerException e) {
            return;
        }
    }
}
