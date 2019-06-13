package sample;


import java.time.LocalDateTime;

public class Update {
    private LocalDateTime date;
    private String originalDescription;
    private String currentDescription;
    private Event event;
    private Update previous;

    public Update(Event event, String update) {
        this.event = event;
        this.originalDescription = update;
        this.currentDescription = update;
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
}
