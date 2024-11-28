package mvc.models;

import org.bson.types.ObjectId;

public class StaffModel {
    private String firstName, lastName, position;
    private ObjectId id;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPosition() {
        return this.position;
    }
}
