package mvc.controllers;

import com.mongodb.client.*;
import mvc.models.StaffModel;
import org.bson.Document;

public class AddStaffController {

    private StaffModel model;

    public AddStaffController(StaffModel model) {
        this.model = model;
        String uri = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");

            MongoCollection<Document> collection = database.getCollection("medical staff");

            Document document = new Document("First Name", model.getFirstName())
                    .append("Last Name", model.getLastName())
                    .append("Position", model.getPosition());

            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
