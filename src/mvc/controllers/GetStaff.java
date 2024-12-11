package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GetStaff {
    public List<Document> getStaffData() {

        List<Document> staffList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");

            MongoCollection<Document> collection = database.getCollection("medical staff");

            for (Document doc : collection.find()) {
                staffList.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!staffList.isEmpty()) {
            return staffList;
        }
        return null;
    }
}
