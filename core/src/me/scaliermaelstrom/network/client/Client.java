package me.scaliermaelstrom.network.client;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import me.scaliermaelstrom.network.NetworkMember;
import me.scaliermaelstrom.network.Message;

public class Client extends NetworkMember {

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    public Client(String hostname) {
        SocketHints hints = new SocketHints();
        hints.performancePrefBandwidth = 1;
        hints.performancePrefConnectionTime = 0;
        hints.performancePrefLatency = 2;
        socket = Gdx.net.newClientSocket(Net.Protocol.TCP, hostname, 9876, hints);

        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public Message read() {
        try {
            String line = reader.readLine();
            if (line == null)
                return null;
            return Message.deserialize(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void send(Message message) {
        try {
            writer.write(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
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
