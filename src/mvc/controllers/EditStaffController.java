package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import mvc.models.StaffModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;

public class EditStaffController {
    StaffModel model;
    public EditStaffController(StaffModel model) {
        this.model = model;
        String uri = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");

            MongoCollection<Document> collection = database.getCollection("medical staff");

            ObjectId id = model.getId();
            String newFirstName = model.getFirstName();
            String newLastName = model.getLastName();
            String newPosition = model.getPosition();

            updateStaffData(collection, id, newFirstName, newLastName, newPosition);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void updateStaffData(MongoCollection<Document> collection, ObjectId id, String firstName, String lastName, String position) {
            Bson filter = Filters.eq("_id", id);

            Bson update = Updates.combine(
                    Updates.set("First Name", firstName),
                    Updates.set("Last Name", lastName)  ,
                    Updates.set("Position", position)
            );

            UpdateResult result = collection.updateOne(filter, update);

            if (result.getMatchedCount() > 0) {
                System.out.println("Document updated successfully.");
            } else {
                System.out.println("No document found with the given ID.");
            }
        }
    }
