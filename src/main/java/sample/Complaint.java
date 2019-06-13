package sample;

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
    enum complaintStat{ Pending, Approved }
}


