package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import database.URI;
import mvc.models.PatientModel;
import org.bson.Document;
import org.bson.conversions.Bson;

public class EditPatientController {
    private PatientModel patientModel;

    public EditPatientController(PatientModel model) {
        this.patientModel = model;

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            updatePatientData(collection);

            if (patientModel.getRoom() != null && !patientModel.getRoom().equals("Select Room")) {
                movePatientToNewRoom(mongoClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePatientData(MongoCollection<Document> collection) {
        Bson filter = Filters.eq("_id", patientModel.getId());

        Bson update = Updates.combine(
                Updates.set("First Name", patientModel.getFirstName()),
                Updates.set("Last Name", patientModel.getLastName()),
                Updates.set("Middle Name", patientModel.getMiddleName()),
                Updates.set("Municipality", patientModel.getMunicipality()),
                Updates.set("Sex", patientModel.getSex()),
                Updates.set("Email", patientModel.getEmail()),
                Updates.set("Region", patientModel.getRegion()),
                Updates.set("Street Name", patientModel.getStreetName()),
                Updates.set("City", patientModel.getCity()),
                Updates.set("Civil Status", patientModel.getCivilStatus()),
                Updates.set("Assigned Staff", patientModel.getAssignedStaff()),
                Updates.set("Symptoms", patientModel.getSymptoms()),
                Updates.set("Medication", patientModel.getMedication()),
                Updates.set("Allergies", patientModel.getAllergies()),
                Updates.set("Phone Number", patientModel.getPhoneNumber()),
                Updates.set("Emergency Contact Number", patientModel.getEmergencyContactNumber()),
                Updates.set("Postal Code", patientModel.getPostalCode()),
                Updates.set("Nationality", patientModel.getNationality()),
                Updates.set("Birthdate", patientModel.getBirthdate()),
                Updates.set("Admission Date", patientModel.getAdmissionDate())
        );

        UpdateResult result = collection.updateOne(filter, update);
        if (result.getMatchedCount() > 0) {
            System.out.println("Document updated successfully.");
        } else {
            System.out.println("No document found with the given ID.");
        }
    }

    private void movePatientToNewRoom(MongoClient mongoClient) {
        try {
            MongoDatabase roomDB = mongoClient.getDatabase("roomDB");
            MongoCollection<Document> roomCollection = roomDB.getCollection("rooms");


            // If the room is changing, we need to remove the patient from the old room
            if (patientModel.getOldRoom() != null || !patientModel.getOldRoom().equals(patientModel.getRoom())) {
                Bson oldRoomFilter = Filters.eq("Room Name", patientModel.getOldRoom());
                Bson removePatientFromOldRoom = Updates.unset("Patients." + patientModel.getId());  // Remove the patient by their ID
                UpdateResult oldRoomUpdate = roomCollection.updateOne(oldRoomFilter, removePatientFromOldRoom);

                if (oldRoomUpdate.getMatchedCount() > 0) {
                    System.out.println("Patient successfully removed from the old room.");
                } else {
                    System.out.println("No matching old room found for the patient.");
                }
            }

            // Add the patient to the new room
            Bson newRoomFilter = Filters.eq("Room Name", patientModel.getRoom());
            Bson addPatientToNewRoom = Updates.set("Patients." + patientModel.getId(), patientModel.getFirstName() + " " + patientModel.getMiddleName() + " " + patientModel.getLastName());
            UpdateResult newRoomUpdate = roomCollection.updateOne(newRoomFilter, addPatientToNewRoom);

            if (newRoomUpdate.getMatchedCount() > 0) {
                System.out.println("Patient successfully moved to the new room.");
            } else {
                System.out.println("No matching room found to move the patient.");
            }
        } catch (Exception e) {
            System.err.println("Error moving patient between rooms: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
