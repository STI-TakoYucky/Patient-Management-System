package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import mvc.models.StaffModel;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DeleteStaffController {
    public DeleteStaffController(StaffModel model) {

        try(MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");
            MongoCollection<Document> collection = database.getCollection("medical staff");

            String id = model.getId();
            Document deleteOneFilter = new Document("_id", id);

            collection.deleteOne(deleteOneFilter);
            System.out.println("Staff deleted successfully");
        } catch(Exception e) {

        }
    }
}
