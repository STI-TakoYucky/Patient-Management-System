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

    // Existing method to get patient data
    public static List<Document> getPatientData() {
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
        return patientList.isEmpty() ? null : patientList;
    }

    // Existing method to get patient data by ID
    public Document getPatientDataById(String patientID) {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");
            Document patientDoc = collection.find(new Document("_id", patientID)).first();
            return patientDoc;
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }


    public List<Document> getPatientDataByInputtedText(String text) {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            // Build a query to search by firstName, middleName, lastName, or _id
            Document query = new Document("$or", List.of(
                    new Document("_id", text), // Exact match for ID
                    new Document("First Name", new Document("$regex", text).append("$options", "i")), // Case-insensitive match
                    new Document("Middle Name", new Document("$regex", text).append("$options", "i")),
                    new Document("Last Name", new Document("$regex", text).append("$options", "i"))
            ));

            // Execute the query and get the matching documents
            List<Document> patientDocs = collection.find(query).into(new ArrayList<>());

            if (!patientDocs.isEmpty()) {
                for (Document patientDoc : patientDocs) {
                    String firstName = patientDoc.getString("firstName");
                    String middleName = patientDoc.getString("middleName");
                    String lastName = patientDoc.getString("lastName");

                    System.out.println("Patient Name: " +
                            (firstName != null ? firstName : "") + " " +
                            (middleName != null ? middleName : "") + " " +
                            (lastName != null ? lastName : ""));
                }
            } else {
                System.out.println("No patients found matching the input: " + text);
            }

            return patientDocs;
        } catch (Exception err) {
            System.err.println("An error occurred while fetching patient data: " + err.getMessage());
            err.printStackTrace();
        }
        return new ArrayList<>(); // Return an empty list if no patients are found or an error occurs
    }



    // Existing method to get patient admission date by ID
    public static String getPatientAdmissionDate(String patientID) {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            Document patientDoc = collection.find(new Document("_id", patientID)).first();

            if (patientDoc != null) {
                Date admissionDate = patientDoc.getDate("Admission Date");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, yyyy h:mm a");
                return simpleDateFormat.format(admissionDate);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return null;
    }

    // Existing method to filter patient data by search field input
    public static List<Document> filterPatientData(String searchFieldInput) {
        List<Document> patientList = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            String searchText = searchFieldInput.trim();

            if (!searchText.isEmpty()) {
                FindIterable<Document> results = collection.find(Filters.or(
                        Filters.regex("First Name", "^" + searchText, "i"),
                        Filters.regex("Last Name", "^" + searchText, "i"),
                        Filters.regex("Middle Name", "^" + searchText, "i"),
                        Filters.regex("_id", "^" + searchText, "i")
                ));

                for (Document doc : results) {
                    patientList.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patientList.isEmpty() ? null : patientList;
    }

    // New method to get the count of patients in the database
    public static long getPatientCount() {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("patientDB");
            MongoCollection<Document> collection = database.getCollection("patients");

            // Get the count of documents in the collection
            long patientCount = collection.countDocuments();
            return patientCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if any exception occurs
    }
}
