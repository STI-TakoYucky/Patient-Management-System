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
import mvc.views.RoomView;
import mvc.views.utility.IDGenerator;
import org.bson.Document;
import org.bson.conversions.Bson;

public class AddPatientController {
    private PatientModel patientModel;
    private RoomView roomView;
    String patientId = IDGenerator.generateShortId(); // Generate the patient ID

    public AddPatientController(PatientModel model, RoomView roomView) {
        this.patientModel = model;
        this.roomView = roomView;
        addPatient();
    }

    public void addPatient() {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase patientDB = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> patientCollection = patientDB.getCollection("patients");
            MongoCollection<Document> isPatientAssigned = patientDB.getCollection("isPatientsAssigned");

            Document patientDocument = createPatientDocument();
            try {
                // Insert the patient into isPatientAssigned collection
                isPatientAssigned.insertOne(new Document("_id", patientId).append("Assigned", false));
            } catch (Exception e) {
                System.err.println("Error inserting into 'isPatientAssigned': " + e.getMessage());
            }

            // Insert the patient data
            patientCollection.insertOne(patientDocument);


            System.out.println("Patient added successfully!");
        } catch (Exception e) {
            System.err.println("Error inserting patient data: " + e.getMessage());
            e.printStackTrace();
        }

        // Update the UI
        roomView.updateUI();
        roomView.revalidate();
        roomView.repaint();
    }

    private Document createPatientDocument() {
        return new Document("_id", patientId)
                .append("Assigned", false)
                .append("Discharged", false)
                .append("First Name", patientModel.getFirstName())
                .append("Last Name", patientModel.getLastName())
                .append("Middle Name", patientModel.getMiddleName())
                .append("Sex", patientModel.getSex())
                .append("Email", patientModel.getEmail())
                .append("Region", patientModel.getRegion())
                .append("Street Name", patientModel.getStreetName())
                .append("City", patientModel.getCity())
                .append("Municipality", patientModel.getMunicipality())
                .append("Civil Status", patientModel.getCivilStatus())
                .append("Assigned Staff", patientModel.getAssignedStaff())
                .append("Phone Number", patientModel.getPhoneNumber())
                .append("Emergency Contact Number", patientModel.getEmergencyContactNumber())
                .append("Postal Code", patientModel.getPostalCode())
                .append("Birthdate", patientModel.getBirthdate())
                .append("Admission Date", patientModel.getAdmissionDate())
                .append("Nationality", patientModel.getNationality())
                .append("Symptoms", patientModel.getSymptoms())
                .append("Medication", patientModel.getMedication())
                .append("Allergies", patientModel.getAllergies());
    }

    private void addPatientToRoom(MongoClient mongoClient) {
        try {
            MongoDatabase roomDB = mongoClient.getDatabase("roomDB");
            MongoCollection<Document> roomCollection = roomDB.getCollection("rooms");

            Bson roomFilter = Filters.eq("Room Name", patientModel.getRoom());
            Bson addPatientToRoom = Updates.set("Patients." + patientModel.getId(), patientModel.getFirstName() + " " + patientModel.getMiddleName() + " " + patientModel.getLastName());

            UpdateResult updateResult = roomCollection.updateOne(roomFilter, addPatientToRoom);

            if (updateResult.getMatchedCount() > 0) {
                System.out.println("Patient successfully added to the room.");
            } else {
                System.out.println("No matching room found to add the patient.");
            }
        } catch (Exception e) {
            System.err.println("Error adding patient to the room: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

