package mvc.controllers;

import com.mongodb.client.*;
import mvc.models.AddStaffModel;
import mvc.views.AddStaffView;
import org.bson.Document;

public class AddStaffController {

    private AddStaffModel model;

    public AddStaffController(AddStaffModel model) {
        this.model = model;
        String uri = "mongodb://localhost:27017";

        // Connect to the MongoDB client
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // Access the database
            MongoDatabase database = mongoClient.getDatabase("staffDB");

            // Access the collection
            MongoCollection<Document> collection = database.getCollection("medical staff");

            // Create a document to insert
            Document document = new Document("First Name", model.getFirstName())
                    .append("Last Name", model.getLastName())
                    .append("Position", model.getPosition());

            // Insert the document
            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
