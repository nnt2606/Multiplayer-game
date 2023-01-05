package client;

import message.csmessage.*;
import message.csmessage.chat.CsChat;
import message.csmessage.roommanagement.CsJoinRoom;
import message.csmessage.session.CsInitSession;
import message.csmessage.usermanagement.CsLogin;
import utility.JsonUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;

    private BufferedInputStream serverInput = null;
    private BufferedOutputStream serverOutput = null;

    // constructor to put ip address and port
    public Client(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // takes input from terminal
            serverInput = new BufferedInputStream(socket.getInputStream());

            // sends output to the socket
            serverOutput = new BufferedOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }

        new Thread(() -> {
            listenMessage();
        }).start();

        // string to read message from input
        String line = "";

        CsInitSession csInitSession = new CsInitSession();
        sendMessage(csInitSession);

        try {
            System.out.println("Client go to sleep for 5 seconds");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CsLogin csLogin = new CsLogin("username", "password");
        sendMessage(csLogin);

        try {
            System.out.println("Client go to sleep for 5 seconds");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            System.out.println("Client go to sleep for 5 seconds");
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        CsJoinRoom csJoinRoom = new CsJoinRoom("61617013-2dd7-4ba1-ac1a-5e7cade18e64");
//        CsMessage csGetRoomList = new CsGetRoomList(5);
        sendMessage(csJoinRoom);

        try {
            System.out.println("Client go to sleep for 5 seconds");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        boolean isRunning = true;
        while (isRunning) {
            CsChat csChat = new CsChat("Hello");
            sendMessage(csChat);

            try {
                System.out.println("Client go to sleep for 10 seconds");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        try {
//            System.out.println("Client go to sleep for 90 seconds");
//            Thread.sleep(90000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        CsLeaveRoom csLeaveRoom = new CsLeaveRoom();
//        sendMessage(csLeaveRoom);
//
//        try {
//            System.out.println("Client go to sleep for 5 seconds");
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        CsJoinRoom csJoinRoom = new CsJoinRoom("password");
//        sendMessage(csJoinRoom);
//        listenMessage();
//
//        try {
//            System.out.println("Client go to sleep for 5 seconds");
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        // close the connection
        try {
            input.close();
            serverInput.close();
            serverOutput.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        Client client = new Client("192.168.1.6", 11000);
    }

    public void sendMessage(CsMessage message) {
        String sendString = JsonUtils.messageToJson(message);
        // Translate the passed message into ASCII and store it as a Byte array.
        byte[] data = sendString.getBytes(StandardCharsets.US_ASCII);

        // Send the message to the connected TcpServer.
        try {
            serverOutput.write(data, 0, data.length);
            serverOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sent:" + sendString);
    }

    private void listenMessage() {
        // Buffer to store the response bytes.
        boolean isRunning = true;
        while (isRunning) {
            byte[] data = new byte[1024];

            // String to store the response ASCII representation.
            String responseData = "";

            // Read the first batch of the TcpServer response bytes.
            try {
                int bytes = serverInput.read(data);
                responseData = new String(data, 0, bytes, StandardCharsets.US_ASCII);
                System.out.println("Received: " + responseData);
            } catch (IOException e) {
                isRunning = false;
                e.printStackTrace();
            }
        }
    }
}