package mvc.controllers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import database.URI;
import mvc.models.PatientModel;
import mvc.views.utility.IDGenerator;
import org.bson.Document;

public class AddPatientController {
    private PatientModel patientModel;

    // Constructor to initialize the controller with a PatientModel
    public AddPatientController(PatientModel model) {
        this.patientModel = model;
        addPatient();
    }

    // Method to add a patient to the database
    public void addPatient() {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            Document document = new Document("_id", IDGenerator.generateShortId())
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
                    .append("Room", patientModel.getRoom())
                    .append("Assigned Staff", patientModel.getAssignedStaff())
                    .append("Phone Number", patientModel.getPhoneNumber())
                    .append("Emergency Contact Number", patientModel.getEmergencyContactNumber())
                    .append("Postal Code", patientModel.getPostalCode())
                    .append("Birthdate", patientModel.getBirthdate())
                    .append("Admission Date", patientModel.getAdmissionDate())
                    .append("Symptoms", patientModel.getSymptoms())
                    .append("Medication", patientModel.getMedication())
                    .append("Allergies", patientModel.getAllergies());

            collection.insertOne(document);

            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
