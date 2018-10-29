package calenderViews;

import java.util.Date;

/**
 * Created by dev on 24/11/17.
 */

public class EventObjects {
    private int id;
    private String message,eventType;
    private Date date;
    private boolean isSelected = false;

    public EventObjects(int id, String message, Date date,String eventType) {
        this.date = date;
        this.message = message;
        this.id = id;
        this.eventType = eventType;
    }
    public int getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return date;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
