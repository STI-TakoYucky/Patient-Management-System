package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import mvc.models.RoomModel;
import org.bson.Document;

public class DeleteRoomController {
    public DeleteRoomController(String roomID) {

        try(MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("roomDB");
            MongoCollection<Document> collection = database.getCollection("rooms");

            String id = roomID;
            Document deleteOneFilter = new Document("_id", id);

            collection.deleteOne(deleteOneFilter);
            System.out.println("Room deleted successfully");
        } catch(Exception e) {

        }
    }
}
