package mvc.controllers;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
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

    public Document getPatientDataById(String patientID) {

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");
            Document patientDoc = collection.find(new Document("_id", patientID)).first();
            System.out.println(patientDoc);
            System.out.println();
            return (Document) patientDoc;
        }catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    public static Document filterPatientData(String searchFieldInput) {
        List<Document> patientList = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");

            MongoCollection<Document> collection = database.getCollection("patients");

            String searchText = searchFieldInput.trim();
            FindIterable<Document> results = collection.find(Filters.regex("name", searchText, "i"));
            for (Document doc : results) {
                patientList.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!patientList.isEmpty()) {
            return (Document) patientList;
        }
        return null;
    }
}
