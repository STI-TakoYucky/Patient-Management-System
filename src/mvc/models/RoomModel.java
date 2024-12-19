package mvc.models;

import java.util.HashMap;
import java.util.Map;

public class RoomModel {
    String roomName, roomType, roomID;
    int roomCapacity;
    Map<String, String> assignedPatients = new HashMap<>();

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public Map<String, String> getAssignedPatients() {
        return assignedPatients;
    }

    public void setAssignedPatients(Map<String, String> assignedPatients) {
        this.assignedPatients = assignedPatients;
    }
}
