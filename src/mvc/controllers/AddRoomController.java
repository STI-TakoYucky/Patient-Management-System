package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import mvc.models.RoomModel;
import mvc.views.utility.IDGenerator;
import org.bson.Document;

public class AddRoomController {
    RoomModel model;
    public AddRoomController(RoomModel model) {
        this.model = model;


        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("roomDB");

            MongoCollection<Document> collection = database.getCollection("rooms");

            Document document = new Document("_id", IDGenerator.generateRoomId())
                    .append("Room Name", model.getRoomName())
                    .append("Room Type", model.getRoomType())
                    .append("Room Capacity", model.getRoomCapacity())
                    .append("Patients", model.getAssignedPatients());

            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
