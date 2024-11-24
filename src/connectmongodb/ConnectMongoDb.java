package connectmongodb;

import com.mongodb.client.*;
import org.bson.Document;

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
