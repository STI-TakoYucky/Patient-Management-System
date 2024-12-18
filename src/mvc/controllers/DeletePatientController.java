package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import mvc.models.PatientModel;
import mvc.models.StaffModel;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DeletePatientController {
    public DeletePatientController(String patientID) {

        try(MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            Document deleteOneFilter = new Document("_id", patientID);

            collection.deleteOne(deleteOneFilter);
            System.out.println("Staff deleted successfully");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
