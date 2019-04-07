package harris.steven.keepmms;

import java.sql.Timestamp;

public class Message {
    private final String UID;
    private final String message;
    private final Timestamp timestamp;
    private final boolean myMessage;


    public Message(String UID, String message, Timestamp timestamp, boolean myMessage){
        this.UID = UID;
        this.message = message;
        this.timestamp = timestamp;
        this.myMessage = myMessage;
    }

    public String getUID() {
        return UID;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isMyMessage() {
        return myMessage;
    }
}
