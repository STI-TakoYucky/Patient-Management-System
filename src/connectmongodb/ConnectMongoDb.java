package connectmongodb;

import com.mongodb.client.*;
import org.bson.Document;

public class ConnectMongoDb {
    public static void main(String[] args) {
        // MongoDB connection URI
        String uri = "mongodb://localhost:27017/";

        // Create a MongoDB client
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Connect to the database (it will be created if it doesn't exist)
            System.out.println("sds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
