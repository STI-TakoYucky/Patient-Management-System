package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GetRooms {
    public List<Document> getRoomData() {

        List<Document> staffList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("roomDB");

            MongoCollection<Document> collection = database.getCollection("rooms");

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

    public Document getRoomDataByID(String id) {

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("roomDB");
            MongoCollection<Document> collection = database.getCollection("rooms");
            Document patientDoc = collection.find(new Document("_id", id)).first();
            System.out.println(patientDoc);
            System.out.println();
            return (Document) patientDoc;
        }catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }
}
