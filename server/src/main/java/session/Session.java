package session;

import entity.game.Game;
import entity.room.Room;
import entity.room.RoomManager;
import entity.user.User;
import exceptions.game.InvalidItemObtainedException;
import exceptions.game.NotEnoughPlayerException;
import exceptions.roommanagement.*;
import exceptions.uservalidation.*;
import message.csmessage.CsMessage;
import message.csmessage.chat.CsChat;
import message.csmessage.game.CsCharacterState;
import message.csmessage.game.CsUserObtainItem;
import message.scmessage.ScMessage;
import message.scmessage.chat.ScChat;
import message.scmessage.game.ScGameStart;
import message.scmessage.game.ScUserObtainItem;
import message.scmessage.roommanagement.*;
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
    private Room room;
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

    public void onJoinRoom(String roomID, String password/*, String avatar*/) {
        try {
            Room room = RoomManager.getInstance().getRoom(roomID);
            // Check if user log in
            if (this.user == null) {
                sendMessage(new ScJoinRoom(403));
            }
            room.addUser(user, password);
            this.room = room;
            ScJoinRoom scJoinRoom = new ScJoinRoom(200, user);
            room.forwardMessage(scJoinRoom);
        } catch (RoomNotExistException e) {
            e.printStackTrace();
            sendMessage(new ScJoinRoom(403));
        } catch (RoomIsFullException e) {
            e.printStackTrace();
            sendMessage(new ScJoinRoom(403));
        } catch (IncorrectRoomPasswordException e) {
            e.printStackTrace();
            sendMessage(new ScJoinRoom(403));
        }
    }

    public void onCreateRoom(String roomName, String password /*, String avatar*/) {
        // Check if login and not in a room
        if (this.user != null && this.room == null) {
            Room room = new Room(roomName, password);
            // Try to catch exception when joining room but it should not have any exception here
            try {
//                user.setAvatar(avatar);
                room.addUser(user, password);
                this.room = room;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendMessage(new ScCreateRoom(room.getRoomID().toString(), 200));
        } else {
            sendMessage(new ScCreateRoom(null, 403));
        }
    }

    public void onLeaveRoom() {
        // Check if user log in and in a room
        if (user != null & room != null) {
            System.out.println(room.getRoomID().toString());
            System.out.println("Remove user");
            this.room.forwardMessage(new ScLeaveRoom(200, user));
            this.room.removeUser(user);
            this.room = null;
        } else {
            sendMessage(new ScLeaveRoom(403));
        }
    }

    public void onGetRoomList(int number) {
        sendMessage(new ScGetRoomList(number));
    }

    public void onGetRoom(String roomID) {
        sendMessage(new ScGetRoom(roomID));
    }

    public void onChat(CsChat message) {
        if (room != null && user != null) {
            ScChat scChat = new ScChat(message.message, this.user.getUserID().toString());
            room.forwardMessage(scChat);
        }
    }

    public void onChangeAvatar(String avatar) {
        if (user != null) {
            user.setAvatar(avatar);
        }
    }

    public void onGameStart() {
        if (room != null) {
            if (user.equals(room.getRoomMaster())) {
                try {
                    Game game = new Game(room);
                    new Thread(() -> {
                        game.sendInitialEnemyState();
                        game.sendInitialItemState();
                    }).run();
                } catch (NotEnoughPlayerException e) {
                    e.printStackTrace();
                    room.forwardMessage(new ScGameStart());
                }
            } else {
                room.forwardMessage(new ScGameStart());
            }
        }
    }

    public void onUserObtainItem(CsUserObtainItem userObtainItem) {
        try {
            user.getGame().userObtainItem(userObtainItem, user);
        } catch (InvalidItemObtainedException e) {
            e.printStackTrace();
            ScUserObtainItem scUserObtainItem = new ScUserObtainItem();
            forwardMessage(scUserObtainItem);
        }
    }

    public void onCharacterState(CsCharacterState characterState) {
        user.getGame().updatePlayerState(characterState);
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
        } finally {
            if (this.room != null && this.user != null) {
                this.room.removeUser(this.user);
            }
            if (this.user != null) {
                this.user.leaveGame();
            }
        }
    }

    public void leaveRoom() {
        this.room = null;
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