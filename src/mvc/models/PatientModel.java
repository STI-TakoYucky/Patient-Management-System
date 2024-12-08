package mvc.models;

import java.util.ArrayList;
import java.util.Date;

public class PatientModel {
    private String firstName, lastName, middleName, id, sex, email, region,  streetName, city, civilStatus;
    private String room, phoneNumber, emergencyContactNumber, assignedStaff;
    private ArrayList<String> symptoms, medication, allergies;
    private int  postalCode;
    private Date birthdate, admissionDate;

    // Setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setAssignedStaff(String assignedStaff) {
        this.assignedStaff = assignedStaff;
    }

    public void setSymptoms(ArrayList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public void setMedication(ArrayList<String> medication) {
        this.medication = medication;
    }

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = allergies;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmergencyContactNumber(String emergencyContactNumber) {
        this.emergencyContactNumber = emergencyContactNumber;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    // Getters
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getId() {
        return this.id;
    }

    public String getSex() {
        return this.sex;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRegion() {
        return this.region;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public String getCity() {
        return this.city;
    }

    public String getCivilStatus() {
        return this.civilStatus;
    }

    public String getRoom() {
        return this.room;
    }

    public String getAssignedStaff() {
        return this.assignedStaff;
    }

    public ArrayList<String> getSymptoms() {
        return this.symptoms;
    }

    public ArrayList<String> getMedication() {
        return this.medication;
    }

    public ArrayList<String> getAllergies() {
        return this.allergies;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmergencyContactNumber() {
        return this.emergencyContactNumber;
    }

    public int getPostalCode() {
        return this.postalCode;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public Date getAdmissionDate() {
        return this.admissionDate;
    }
}
