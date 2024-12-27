package mvc.controllers;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
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

    public List<Document> getAdminData() {

        List<Document> staffList = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("adminDB");

            MongoCollection<Document> collection = database.getCollection("admins");

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

    public static List<Document> filterStaffData(String searchFieldInput) {
        List<Document> staffList = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");
            MongoCollection<Document> collection = database.getCollection("medical staff");

            String searchText = searchFieldInput.trim();

            if (!searchText.isEmpty()) {
                FindIterable<Document> results = collection.find(Filters.or(
                        Filters.regex("First Name", "^" + searchText, "i"),
                        Filters.regex("Last Name", "^" + searchText, "i"),
                        Filters.regex("_id", "^" + searchText, "i")
                ));

                for (Document doc : results) {
                    staffList.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return staffList.isEmpty() ? null : staffList;
    }

    public List<Document> filterAdminData(String searchFieldInput) {
        List<Document> adminList = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("adminDB");
            MongoCollection<Document> collection = database.getCollection("admins");

            String searchText = searchFieldInput.trim();

            if (!searchText.isEmpty()) {
                FindIterable<Document> results = collection.find(Filters.or(
                        Filters.regex("First Name", "^" + searchText, "i"),
                        Filters.regex("Last Name", "^" + searchText, "i"),
                        Filters.regex("_id", "^" + searchText, "i")
                ));

                for (Document doc : results) {
                    adminList.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adminList.isEmpty() ? null : adminList;
    }

    public static long getStaffCount() {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("staffDB");
            MongoCollection<Document> collection = database.getCollection("medical staff");

            // Get the count of documents in the collection
            long staffCount = collection.countDocuments();
            return staffCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if any exception occurs
    }

    public static long getAdminCount() {
        try (MongoClient mongoClient = MongoClients.create(URI.URI)) {
            MongoDatabase database = mongoClient.getDatabase("adminDB");
            MongoCollection<Document> collection = database.getCollection("admins");

            // Get the count of documents in the collection
            long adminCount = collection.countDocuments();
            return adminCount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if any exception occurs
    }
}
