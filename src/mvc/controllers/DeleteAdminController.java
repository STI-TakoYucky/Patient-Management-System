package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import mvc.models.StaffModel;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DeleteAdminController {
    public DeleteAdminController(String id) {

        try(MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("adminDB");
            MongoCollection<Document> collection = database.getCollection("admins");

            Document deleteOneFilter = new Document("_id", id);

            collection.deleteOne(deleteOneFilter);
            System.out.println("Staff deleted successfully");
        } catch(Exception e) {

        }
    }
}
