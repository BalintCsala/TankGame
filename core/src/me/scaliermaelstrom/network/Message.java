package me.scaliermaelstrom.network;


public class Message {

    public String header;
    public String data;

    public Message(String header, String data) {
        this.header = header;
        this.data = data;
    }

    @Override
    public String toString() {
        return (char) header.length() + header + data;
    }

    public static Message deserialize(String msg) {
        int headerLength = Character.getNumericValue(msg.charAt(0));
        return new Message(
                msg.substring(1, headerLength + 1),
                msg.substring(headerLength + 1, msg.length())
        );
    }
}
