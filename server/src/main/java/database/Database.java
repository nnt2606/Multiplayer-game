package database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Database {
    private static MongoDatabase DATABASE;

    public static MongoDatabase getConnection() {
        if (DATABASE == null) {
            ConnectionString connectionString = new ConnectionString("mongodb+srv://team12:ahihi1234@cluster0.0esyhx6.mongodb.net/?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            DATABASE = mongoClient.getDatabase("MultiplayerGame");
        }
        return DATABASE;
    }
}
