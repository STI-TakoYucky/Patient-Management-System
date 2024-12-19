package mvc.controllers;

import com.mongodb.client.*;
import mvc.models.StaffModel;
import org.bson.Document;
import mvc.views.utility.IDGenerator;
import database.URI;

public class AddStaffController {

    private StaffModel model;

    public AddStaffController(StaffModel model) {
        this.model = model;


        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");

            MongoCollection<Document> collection = database.getCollection("medical staff");

            Document document = new Document("_id", IDGenerator.generateShortId())
                    .append("Role", "Staff")
                    .append("First Name", model.getFirstName())
                    .append("Middle Name", model.getMiddleName())
                    .append("Last Name", model.getLastName())
                    .append("Position", model.getPosition())
            .append("Username", model.getUsername())
            .append("Password", model.getPassword());

            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
