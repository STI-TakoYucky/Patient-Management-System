package mvc.controllers;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import database.URI;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static String getPatientAdmissionDate(String patientID) {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            Document patientDoc = collection.find(new Document("_id", patientID)).first();

            if (patientDoc != null) {
                Date admissionDate = patientDoc.getDate("Admission Date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy h:mm a");
                String formattedDate = simpleDateFormat.format(admissionDate);
                System.out.println("Admission Date: " + formattedDate);
                return formattedDate;
            } else {
                System.out.println("Patient not found.");
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }


    public static List<Document> filterPatientData(String searchFieldInput) {
        List<Document> patientList = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            String searchText = searchFieldInput.trim();

            if (!searchText.isEmpty()) {
                // Filters for the search criteria
                FindIterable<Document> results = collection.find(Filters.or(
                        Filters.regex("First Name", "^" + searchText, "i"),  // Starts with
                        Filters.regex("Last Name", "^" + searchText, "i"),   // Starts with
                        Filters.regex("Middle Name", "^" + searchText, "i"), // Starts with
                        Filters.regex("_id", "^" + searchText, "i")
                ));

                // Add the filtered documents to the list
                for (Document doc : results) {
                    patientList.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the list of documents (patients)
        return (List<Document>) patientList;
    }
}
