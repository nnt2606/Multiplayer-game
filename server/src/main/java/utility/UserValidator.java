package utility;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.Database;
import exceptions.roommanagement.WrongLoginPasswordException;
import exceptions.roommanagement.WrongLoginUsernameException;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {
    private static final Map<String, String> cacheMemory = new HashMap<String, String>();

    public static void initialize() {
        MongoDatabase db = Database.getConnection();
        MongoCollection<Document> userCollection = db.getCollection("Users");
        FindIterable<Document> iter = userCollection.find();
        for (Document doc : iter) {
            cacheMemory.put(doc.get("userName").toString(), doc.get("password").toString());
        }
    }

    public static boolean validate(String userName, String password) throws WrongLoginUsernameException, WrongLoginPasswordException {
        if (!cacheMemory.containsKey(userName)) {
            MongoDatabase db = Database.getConnection();
            MongoCollection<Document> userCollection = db.getCollection("Users");
            BasicDBObject query = new BasicDBObject();
            query.append("userName", userName);
            long result = userCollection.countDocuments(query);
            if (result == 0) {
                throw new WrongLoginUsernameException();
            }
            query.append("password", password);
            result = userCollection.countDocuments(query);
            if (result == 0) {
                throw new WrongLoginPasswordException();
            }
            cacheMemory.put(userName, password);
            return true;
        }

        if (!password.equals(cacheMemory.get(userName))) {
            throw new WrongLoginPasswordException();
        }

        return true;
    }

//    public static void main(String[] args) throws Exception {
//        UserValidator.initialize();
//        System.out.println(UserValidator.validate("username", "password"));
//    }
}
