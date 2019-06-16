package sample.actions;

import sample.users.BasicUser;

public class Complaint {

    private BasicUser reported;
    private BasicUser complainedOn;
    private String description;
    private complaintStat status;

    public Complaint(BasicUser reported, BasicUser complainedOn, String description){
        this.reported = reported;
        this.complainedOn = complainedOn;
        this.description = description;
        status = complaintStat.Pending;
    }

    public BasicUser getReported() {
        return reported;
    }

    public BasicUser getComplainedOn() {
        return complainedOn;
    }

    public String getDescription(){
        return description;
    }

    public String getStatus(){
        return status.name();
    }

    public void approve() {
        status = complaintStat.Approved;
    }

    public void decline() {
    }

    public void setStatus(String status){
        this.status = complaintStat.valueOf(status);
    }

    enum complaintStat{ Pending, Approved, Declined }
}


