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
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Map;

public class EditRoomController {
    RoomModel model;

    public EditRoomController(RoomModel model, Map<String, String> oldPatientMap) {
        this.model = model;
        Map<String, String> patientMap = model.getAssignedPatients();

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("roomDB");
            MongoCollection<Document> roomCollection = database.getCollection("rooms");

            // Update room data
            updateRoomData(roomCollection, mongoClient);

            // Access patientsDB and collection
            MongoDatabase patientsDB = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> patients = patientsDB.getCollection("patients");

            // Iterate through new patient map (patients currently assigned)
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
                                Updates.set("Assigned", true) // Set the 'Assigned' field to true
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

            // Iterate through the old patient map to find patients who are no longer in the new map
            for (Map.Entry<String, String> entry : oldPatientMap.entrySet()) {
                String patientId = entry.getKey(); // The patient ID (key)

                // Check if the patient is no longer in the new patient map
                if (!patientMap.containsKey(patientId)) {
                    try {
                        // Find the patient document based on the patient ID
                        Document patientDocument = patients.find(Filters.eq("_id", patientId)).first();

                        if (patientDocument != null) {
                            // Patient found, proceed to update the 'Assigned' field to false
                            System.out.println("Found patient to unassign: " + patientDocument.toJson());

                            // Update the 'Assigned' field to false
                            UpdateResult updateResult = patients.updateOne(
                                    Filters.eq("_id", patientId),
                                    Updates.set("Assigned", false) // Set the 'Assigned' field to false
                            );

                            if (updateResult.getMatchedCount() > 0) {
                                System.out.println("Successfully updated the 'Assigned' field to false for patient with ID: " + patientId);
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
            }

            System.out.println("Room data updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRoomData(MongoCollection<Document> roomCollection, MongoClient mongoClient) {
        Bson filter = Filters.eq("_id", model.getRoomID());


        Bson update = Updates.combine(
                Updates.set("Room Name", model.getRoomName()),
                Updates.set("Room Capacity", model.getRoomCapacity()),
                Updates.set("Room Type", model.getRoomType()),
                Updates.set("Patients", model.getAssignedPatients())
        );

        UpdateResult result = roomCollection.updateOne(filter, update);

        if (result.getMatchedCount() > 0) {
            System.out.println("Room document updated successfully.");
        } else {
            System.out.println("No room document found with the given ID.");
        }

        // Handle patient updates
        movePatientsBetweenRooms(model.getAssignedPatients(), model.getRoomName(), mongoClient);
    }

    private void movePatientsBetweenRooms(Map<String, String> assignedPatients, String roomName, MongoClient mongoClient) {
        MongoDatabase patientDB = mongoClient.getDatabase("patientDB");
        MongoCollection<Document> patientCollection = patientDB.getCollection("patients");

        for (Map.Entry<String, String> entry : assignedPatients.entrySet()) {
            String patientId = entry.getKey();
            String patientName = entry.getValue();

            System.out.println("Processing Patient ID: " + patientId);
            System.out.println("Assigned Room: " + roomName);

            // Update the patient's room property
            Bson patientFilter = Filters.eq("_id", patientId);
            Bson updateRoomProperty;

            if ("Select Room".equals(roomName)) {
                // Clear the room property if "Select Room" is assigned
                updateRoomProperty = Updates.unset("room");
            } else {
                // Update the room property with the current room name
                updateRoomProperty = Updates.set("room", roomName);
            }

            UpdateResult patientUpdateResult = patientCollection.updateOne(patientFilter, updateRoomProperty);

            if (patientUpdateResult.getMatchedCount() > 0) {
                System.out.println("Patient room updated successfully for ID: " + patientId);
            } else {
                System.out.println("No matching patient found for ID: " + patientId);
            }
        }
    }
}
