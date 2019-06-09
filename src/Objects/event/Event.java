package Objects.event;

import java.util.Date;

public class Event {

    private String title;
    private Date date;
    private String status;
    private String details;
    private String[] categories;
    private String userId;

    public Event(String[] categories, String userId, String[] orginizations) {
        this.categories = categories;
        this.userId = userId;
    }
}
