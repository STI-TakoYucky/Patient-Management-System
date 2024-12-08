package mvc.controllers;

import com.mongodb.client.*;
import mvc.models.StaffModel;
import org.bson.Document;
import mvc.views.utility.IDGenerator;

public class AddStaffController {

    private StaffModel model;

    public AddStaffController(StaffModel model) {
        this.model = model;
        String uri = "mongodb+srv://lucky:lucky123patientmanagementsystem.edpel.mongodb.net/?retryWrites=true&w=majority&appName=patientmanagementsystemcluster";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");

            MongoCollection<Document> collection = database.getCollection("medical staff");

            Document document = new Document("_id", IDGenerator.generateShortId())
                    .append("First Name", model.getFirstName())
                    .append("Last Name", model.getLastName())
                    .append("Position", model.getPosition());

            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
