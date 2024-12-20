package mvc.controllers;

import com.mongodb.client.*;
import mvc.models.StaffModel;
import org.bson.Document;
import mvc.views.utility.IDGenerator;
import database.URI;

public class AddAdminController {

    private StaffModel model;

    public AddAdminController(StaffModel model) {
        this.model = model;


        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("adminDB");

            MongoCollection<Document> collection = database.getCollection("admins");

            Document document = new Document("_id", IDGenerator.generateShortId())
                    .append("First Name", model.getFirstName())
                    .append("Middle Name", model.getMiddleName())
                    .append("Last Name", model.getLastName())
                    .append("Role", "Admin")
                    .append("Username", model.getUsername())
                    .append("Password", model.getPassword());

            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
