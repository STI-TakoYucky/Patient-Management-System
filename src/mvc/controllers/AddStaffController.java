package mvc.controllers;

import com.mongodb.client.*;
import mvc.models.AddStaffModel;
import mvc.views.AddStaffView;
import mvc.views.MedicalStaffView;
import org.bson.Document;

public class AddStaffController {

    private AddStaffModel model;

    public AddStaffController(AddStaffModel model) {
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
