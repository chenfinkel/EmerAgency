package sample.actions;


import java.time.LocalDateTime;

public class Update {
    private LocalDateTime date;
    private String originalDescription;
    private String currentDescription;
    private Event event;
    private Update previous;

    public Update(){

    }

    public Update(Event event, String description) {
        this.event = event;
        this.originalDescription = description;
        this.currentDescription = description;
        date = LocalDateTime.now();
    }



    public Update(String description) {
        this.originalDescription = description;
        this.currentDescription = description;
        date = LocalDateTime.now();
    }

    public void setPrevious(Update prev){
        previous = prev;
    }

    public void edit(String newDescription){
        currentDescription = newDescription;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getOriginalDescription() {
        return originalDescription;
    }

    public String getCurrentDescription() {
        return currentDescription;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setCurrentDescription(String currentDescription) {
        this.currentDescription = currentDescription;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setOriginalDescription(String originalDescription) {
        this.originalDescription = originalDescription;
    }

    public Update getPrevious() {
        return previous;
    }
}
