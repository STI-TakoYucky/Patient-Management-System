package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import database.URI;
import mvc.models.RoomModel;
import mvc.views.utility.IDGenerator;
import org.bson.Document;

import java.util.Map;

public class AddRoomController {
    RoomModel model;
    public AddRoomController(RoomModel model) {
        this.model = model;
        Map<String, String> patientMap = model.getAssignedPatients();


        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("roomDB");

            MongoCollection<Document> collection = database.getCollection("rooms");

            MongoDatabase patientsDB = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> patients = patientsDB.getCollection("patients");

            for (Map.Entry<String, String> entry : patientMap.entrySet()) {
                String patientId = entry.getKey();    // The patient ID (key)
                String patientName = entry.getValue(); // The patient name (value)

                try {
                    // Find the patient document based on the patient ID
                    Document patientDocument = patients.find(Filters.eq("_id", patientId)).first();

                    if (patientDocument != null) {
                        // Patient found, proceed to update the 'Assigned' field
                        System.out.println("Found patient: " + patientDocument.toJson());

                        // Update the 'Assigned' field to true (or any value you want to set)
                        UpdateResult updateResult = patients.updateOne(
                                Filters.eq("_id", patientId),
                                Updates.set("Assigned", true) // Change to true or false depending on your use case
                        );

                        if (updateResult.getMatchedCount() > 0) {
                            System.out.println("Successfully updated the 'Assigned' field for patient with ID: " + patientId);
                        } else {
                            System.out.println("No patient with ID: " + patientId + " was found to update.");
                        }
                    } else {
                        // Patient with this ID was not found
                        System.out.println("Patient with ID " + patientId + " not found.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }




            Document document = new Document("_id", IDGenerator.generateRoomId())
                    .append("Room Name", model.getRoomName())
                    .append("Room Type", model.getRoomType())
                    .append("Room Capacity", model.getRoomCapacity())
                    .append("Patients", model.getAssignedPatients());

            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
