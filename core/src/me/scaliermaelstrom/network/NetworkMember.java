package me.scaliermaelstrom.network;


import java.util.ArrayList;

public abstract class NetworkMember {

    public abstract Message read();
    public abstract void send(Message message);
    public abstract void dispose();

}
