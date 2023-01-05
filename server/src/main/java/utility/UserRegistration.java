package utility;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.Database;
import exceptions.uservalidation.RegisterPasswordMismatchException;
import exceptions.uservalidation.UsernameAlreadyExistException;
import org.bson.Document;

public class UserRegistration {
    public static boolean register(String userName, String password, String retypePassword)
            throws RegisterPasswordMismatchException, UsernameAlreadyExistException {
        if (!password.equals(retypePassword)) throw new RegisterPasswordMismatchException();
        if (checkUserAlreadyExist(userName)) throw new UsernameAlreadyExistException();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = new Document();
                document.append("userName", userName);
                document.append("password", password);

                MongoDatabase db = Database.getConnection();
                MongoCollection<Document> userCollection = db.getCollection("Users");
                userCollection.insertOne(document);
            }
        });
        t.setDaemon(true);
        t.start();
        return true;
    }

    private static boolean checkUserAlreadyExist(String userName) {
        MongoDatabase db = Database.getConnection();
        MongoCollection<Document> userCollection = db.getCollection("Users");
        BasicDBObject query = new BasicDBObject();
        query.append("userName", userName);
        long result = userCollection.countDocuments(query);
        return result != 0;
    }
//
//    public static void main(String[] args) {
//        System.out.println(register("username2", "password", "password"));
//    }
}
