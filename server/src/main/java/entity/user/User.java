package entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import entity.game.Game;
import exceptions.uservalidation.WrongLoginPasswordException;
import exceptions.uservalidation.WrongLoginUsernameException;
import lombok.Getter;
import message.scmessage.ScMessage;
import session.Session;
import utility.UserValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class User {
    @JsonIgnore
    private static final Map<String, User> loginMap = new HashMap<String, User>();
    private UUID userID;
    @JsonIgnore
    private Session session;
    @JsonIgnore
    private Game game;
    private String userName;
    private String avatar = "Bunny";

    public User(Session session, String userName, String password, boolean needValidation)
            throws WrongLoginUsernameException, WrongLoginPasswordException {
        if (needValidation) {
            if (UserValidator.validate(userName, password)) {
                if (loginMap.get(userName) != null) {
                    loginMap.get(userName).disconnect();
                }
                this.session = session;
                this.userID = UUID.randomUUID();
                this.userName = userName;
                loginMap.put(userName, this);
                UserManager.getInstance().addUser(this);
            }
        } else {
            this.session = session;
            this.userID = UUID.randomUUID();
            this.userName = userName;
        }
    }

    public void joinGame(Game game) {
        this.game = game;
    }

    public void leaveGame() {
        if (this.game != null) {
            this.game.removeUser(this);
            this.game = null;
        }
    }

    public void forwardMessage(ScMessage message) {
        session.forwardMessage(message);
    }

    public void forwardUDPMessage(ScMessage message) {
        session.forwardUDPMessage(message);
    }

    public void disconnect() {
        loginMap.remove(this.userName);
        this.session.onDisconnect(false);
    }

    @JsonIgnore
    public Session getSession() {
        return this.session;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof User) {
            User user = (User) o;
            if (user.userID.equals(this.userID)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
