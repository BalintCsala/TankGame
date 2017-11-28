package me.scaliermaelstrom.network.server;


import com.badlogic.gdx.net.Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import me.scaliermaelstrom.Tank;

public class User {

    public Socket socket;
    public BufferedWriter writer;
    public BufferedReader reader;

    public User(Socket socket) {
        this.socket = socket;

        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void dispose() {
        try {
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.dispose();
    }
}
