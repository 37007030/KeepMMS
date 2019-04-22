package harris.steven.keepmms;

import java.sql.Timestamp;

public class Message {
    private final String to_ID;
    private final String from_ID;
    private final String message;
    private final Timestamp timestamp;


    public Message(String to_ID, String from_ID, String message, Timestamp timestamp){
        this.to_ID = to_ID;
        this.from_ID = from_ID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getTo_ID() {
        return to_ID;
    }

    public String getFrom_ID() {
        return from_ID;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isMyMessage() {
        return from_ID.equals("0");
    }

    @Override
    public String toString() {
        return "Message{" +
                "to_ID='" + to_ID + '\'' +
                ", from_ID='" + from_ID + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
