package me.scaliermaelstrom.network.server;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import me.scaliermaelstrom.network.NetworkMember;
import me.scaliermaelstrom.network.Message;

public class Server extends NetworkMember implements Runnable {

    private User[] users = new User[2];
    private ServerSocket serverSocket;
    private ArrayList<Message> receivedQueue = new ArrayList<Message>();
    private ArrayList<Message> sentQueue = new ArrayList<Message>();

    public String address = "";
    public boolean searching = false;
    public boolean running = false;

    public Server() {
        ServerSocketHints hints = new ServerSocketHints();
        hints.performancePrefBandwidth = 1;
        hints.performancePrefConnectionTime = 0;
        hints.performancePrefLatency = 2;
        serverSocket = Gdx.net.newServerSocket(Net.Protocol.TCP, 9876, hints);
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        searching = true;
        search();

        try {
            while (running) {
                for (User user : users) {
                    for (Message message : sentQueue) {
                        user.writer.write(message.toString());
                    }
                    String message;
                    while ((message = user.reader.readLine()) != null) {
                        Message msg = Message.deserialize(message);
                        receivedQueue.add(msg);
                    }

                    // Update
                }
                sentQueue.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void search() {
        int connected = 0;
        while (connected < 2) {
            Socket socket = serverSocket.accept(null);
            users[connected] = new User(socket);
            connected++;
        }
        searching = false;
    }

    @Override
    public Message read() {
        if (receivedQueue.size() == 0)
            return null;
        Message msg = receivedQueue.get(0);
        receivedQueue.remove(0);
        return msg;
    }

    @Override
    public void send(Message message) {
        sentQueue.add(message);
        receivedQueue.add(message);
    }

    public void stop() {
        running = false;
    }

    @Override
    public void dispose() {
        for (User user : users) {
            user.dispose();
        }
        serverSocket.dispose();
    }
}
