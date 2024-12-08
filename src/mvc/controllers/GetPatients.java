package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GetPatients {
    public List<Document> getPatientData() {

        List<Document> patientList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");

            MongoCollection<Document> collection = database.getCollection("patients");

            for (Document doc : collection.find()) {
                patientList.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!patientList.isEmpty()) {
            return patientList;
        }
        return null;
    }
}
