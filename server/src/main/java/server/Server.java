package server;// A Java program for a server.Server

import entity.user.UserManager;
import message.csmessage.CsMessage;
import message.csmessage.game.CsCharacterState;
import message.csmessage.session.CsInitSession;
import message.scmessage.ScMessage;
import session.Session;
import utility.JsonUtils;
import utility.UserValidator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    private static Server instance;
    //initialize socket and input stream
    private Socket socket = null;
    private DatagramSocket udpSocket;
    private ServerSocket server = null;
    private BufferedInputStream in = null;
    private BufferedOutputStream out = null;

    // constructor with port
    private Server(int port, int udpPort) {
        instance = this;
        // starts server and waits for a connection
        new Thread(() -> {
            UserValidator.initialize();
        }).start();
        try {
            server = new ServerSocket(port);
            udpSocket = new DatagramSocket(udpPort);
            new Thread(() -> {
                listenUdpPort();
            }).start();
            System.out.println("server.Server started");

            System.out.println("Waiting for a client ...");

            while (true) {
                socket = server.accept();
                System.out.println("Client accepted");

                out = new BufferedOutputStream(socket.getOutputStream());
                in = new BufferedInputStream(socket.getInputStream());

                String line = "";

                try {
                    byte[] data = new byte[256];
                    int bytes = in.read(data);
                    line = new String(data, 0, bytes, StandardCharsets.US_ASCII);
                    System.out.println(line);
                    CsInitSession csInitSession = (CsInitSession) JsonUtils.jsonToMessage(line);
                    Session session = new Session(socket, in, out);
                    csInitSession.onMessage(session);
                    System.out.println("A new session created for communication");
                } catch (IOException i) {
                    i.printStackTrace();
                    System.out.println("Closing connection");
                    socket.close();
                    out.close();
                    in.close();
                }
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static Server getInstance() {
        return instance;
    }

    public static void main(String args[]) {
        byte[] data = new byte[256];
        Server server = new Server(11000, 56000);
    }

    private void listenUdpPort() {
        boolean running = true;
        byte[] buf = new byte[1024];

        System.out.println("UDP port successfully deploy\n");

        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            try {
                udpSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String received = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.US_ASCII);
            System.out.println("UDP socket receive message: " + received);
            CsMessage csMessage = (CsMessage) JsonUtils.jsonToMessage(received);
            if (csMessage instanceof CsCharacterState) {
                CsCharacterState csCharacterState = (CsCharacterState) csMessage;
                Session userSession = UserManager.getInstance().getUser(csCharacterState.getUserId()).getSession();
                csCharacterState.onMessage(userSession);
            }
        }

        System.out.println("UDP port close\n");
        udpSocket.close();
    }

    public void sendUdpMessage(ScMessage message, InetAddress address, int port) {
        String sendString = JsonUtils.messageToJson(message);
        // Translate the passed message into ASCII and store it as a Byte array.
        byte[] data = sendString.getBytes(StandardCharsets.US_ASCII);

        // Send the message to the connected TcpServer.
        try {
            DatagramPacket packet
                    = new DatagramPacket(data, data.length, address, port);
            udpSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sent Udp package:" + sendString);
    }
}