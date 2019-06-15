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

    enum complaintStat{ Pending, Approved }
}


