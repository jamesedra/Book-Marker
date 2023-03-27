package persistence;

import org.json.JSONObject;

public interface Writable {

    // This method was copied from CPSC 210 WorkRoomApp
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
