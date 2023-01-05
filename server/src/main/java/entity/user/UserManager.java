package entity.user;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance = new UserManager();
    private List<User> userList = new ArrayList<User>();

    private UserManager() {
    }

    public static UserManager getInstance() {
        return instance;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUser(String userID) {
        for (User user : userList) {
            if (user.getUserID().toString().equals(userID)) {
                return user;
            }
        }
        return null;
    }
}
