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
import mvc.models.StaffModel;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;

public class EditPatientController {
    PatientModel model;
    public EditPatientController(PatientModel model) {
        this.model = model;

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");

            MongoCollection<Document> collection = database.getCollection("patients");


            updateStaffData(collection);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateStaffData(MongoCollection<Document> collection) {
        Bson filter = Filters.eq("_id", model.getId());

        Bson update = Updates.combine(
                Updates.set("First Name", model.getFirstName()),
                Updates.set("Last Name", model.getLastName()),
                Updates.set("Middle Name", model.getMiddleName()),
                Updates.set("Municipality", model.getMunicipality()),
                Updates.set("Sex", model.getSex()),
                Updates.set("Email", model.getEmail()),
                Updates.set("Region", model.getRegion()),
                Updates.set("Street Name", model.getStreetName()),
                Updates.set("City", model.getCity()),
                Updates.set("Civil Status", model.getCivilStatus()),
                Updates.set("Room", model.getRoom()),
                Updates.set("Assigned Staff", model.getAssignedStaff()),
                Updates.set("Symptoms", model.getSymptoms()),
                Updates.set("Medication", model.getMedication()),
                Updates.set("Allergies", model.getAllergies()),
                Updates.set("Phone Number", model.getPhoneNumber()),
                Updates.set("Emergency Contact Number", model.getEmergencyContactNumber()),
                Updates.set("Postal Code", model.getPostalCode()),
                Updates.set("Birthdate", model.getBirthdate()),
                Updates.set("Admission Date", model.getAdmissionDate())
        );

        UpdateResult result = collection.updateOne(filter, update);

        if (result.getMatchedCount() > 0) {
            System.out.println("Document updated successfully.");
        } else {
            System.out.println("No document found with the given ID.");
        }
    }
}
