package harris.steven.keepmms;

public class Link {
    private final String to_ID;
    private final String from_ID;
    private final String link;
    private String timestamp;


    public Link(String to_ID, String from_ID, String link, String timestamp){
        this.to_ID = to_ID;
        this.from_ID = from_ID;
        this.link = link;
        this.timestamp = timestamp;
    }

    public String getTo_ID() {
        return to_ID;
    }

    public String getFrom_ID() {
        return from_ID;
    }

    public String getLink() {
        return link;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {


        /*
        return "Link{" +
                "to_ID='" + to_ID + '\'' +
                ", from_ID='" + from_ID + '\'' +
                ", link='" + link + '\'' +
                ", timestamp=" + timestamp +
                '}';
        */
        return link;
    }
}
