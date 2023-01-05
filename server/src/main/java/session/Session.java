package session;

import entity.user.User;
import exceptions.uservalidation.*;
import message.csmessage.CsMessage;
import message.csmessage.chat.CsChat;
import message.csmessage.game.CsCharacterState;
import message.csmessage.game.CsUserObtainItem;
import message.scmessage.ScMessage;
import message.scmessage.session.ScCloseSession;
import message.scmessage.session.ScInitSession;
import message.scmessage.usermanagement.ScLogin;
import message.scmessage.usermanagement.ScRegister;
import server.Server;
import utility.JsonUtils;
import utility.SessionTimer;
import utility.UserRegistration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Session {
    private Socket clientSocket;
    private BufferedInputStream clientInputStream;
    private BufferedOutputStream clientOutputStream;
    private User user;
    private SessionTimer sessionTimer;
    private boolean isRunning = true;

    public Session(Socket socket,
                   BufferedInputStream clientInputStream,
                   BufferedOutputStream clientOutputStream) {
        this.clientSocket = socket;
        this.clientInputStream = clientInputStream;
        this.clientOutputStream = clientOutputStream;
        sessionTimer = new SessionTimer(this);
        connect();
    }

    private void connect() {
        new Thread(() -> {
            listenMessage();
        }).start();
        sessionTimer.start();
    }

    public void onConnect() {
        sendMessage(new ScInitSession());
    }

    public void onRegister(String userName, String password, String retypePassword) {
        try {
            UserRegistration.register(userName, password, retypePassword);
            try {
                User user = new User(this, userName, password, false);
                this.user = user;
                sendMessage(new ScRegister(user, 200));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (UsernameAlreadyExistException e) {
            e.printStackTrace();
            sendMessage(new ScRegister(null, 403));
        } catch (RegisterPasswordMismatchException e) {
            e.printStackTrace();
            sendMessage(new ScRegister(null, 400));
        }
    }

    public void onLogin(String userName, String password) {
        try {
            User user = new User(this, userName, password, true);
            this.user = user;
            sendMessage(new ScLogin(user.getUserID().toString(), 200));
        } catch (WrongLoginUsernameException e) {
            sendMessage(new ScLogin(null, 404));
        } catch (WrongLoginPasswordException e) {
            sendMessage(new ScLogin(null, 403));
        }
    }

    public void onJoinRoom(String roomID, String password) {

    }

    public void onCreateRoom(String roomName, String password) {

    }

    public void onLeaveRoom() {

    }

    public void onGetRoomList(int number) {

    }

    public void onGetRoom(String roomID) {

    }

    public void onChat(CsChat message) {

    }

    public void onChangeAvatar(String avatar) {

    }

    public void onGameStart() {

    }

    public void onUserObtainItem(CsUserObtainItem userObtainItem) {

    }

    public void onCharacterState(CsCharacterState characterState) {

    }

    public void onDisconnect(boolean isAlreadyClosed) {
        try {
            System.out.println("Close Connection");
            if (!isAlreadyClosed) {
                sendMessage(new ScCloseSession());
            }
            isRunning = false;
            clientInputStream.close();
            clientOutputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void leaveRoom() {

    }

    public void forwardMessage(ScMessage message) {
        sendMessage(message);
    }

    public void forwardUDPMessage(ScMessage message) {
        sessionTimer.reset();
        InetAddress address = clientSocket.getInetAddress();
        // Send the message to the connected TcpServer.
        Server.getInstance().sendUdpMessage(message, address, 56000);
    }

    private void listenMessage() {
        System.out.println("Session created and listen to TCP port");
        String responseData = "";
        byte[] data = new byte[1024];
        while (isRunning) {
            // Read the first batch of the TcpServer response bytes.
            try {
                int bytes = clientInputStream.read(data);
                responseData = new String(data, 0, bytes, StandardCharsets.US_ASCII);
                System.out.println("Received: " + responseData);
                if (responseData.length() > 0) {
                    sessionTimer.reset();
                    CsMessage receivedMessage = (CsMessage) JsonUtils.jsonToMessage(responseData);
                    new Thread(() -> {
                        receivedMessage.onMessage(this);
                    }).start();
                } else {
                    onDisconnect(false);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                onDisconnect(false);
                break;
            }
        }
    }

    private void sendMessage(ScMessage message) {
        String sendString = JsonUtils.messageToJson(message);
        // Translate the passed message into ASCII and store it as a Byte array.
        byte[] data = sendString.getBytes(StandardCharsets.US_ASCII);

        // Send the message to the connected TcpServer.
        try {
            clientOutputStream.flush();
            clientOutputStream.write(data, 0, data.length);
            clientOutputStream.flush();
        } catch (IOException e) {
            onDisconnect(true);
            e.printStackTrace();
        }

        System.out.println("Sent:" + sendString);
    }
}