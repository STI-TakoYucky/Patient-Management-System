package database;

import com.mongodb.client.*;

public class ConnectMongoDb {

    public void interactWithDatabase() {
        // MongoDB connection URI
        String uri = "mongodb://localhost:27017/";

        try (MongoClient mongoClient = MongoClients.create(uri)) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
