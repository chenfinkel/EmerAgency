package sample.users;

import sample.DBHandler;
import sample.actions.Complaint;
import sample.organizations.Organization;

import java.util.ArrayList;
import java.util.List;

public class BasicUser extends User {
    private int degree;
    private int warnings;
    private userStatus status;
    private List<Complaint> complaints;

    public BasicUser(){
        super();
    }

    public BasicUser(String id, String password, String mail, Organization org, int degree){
        super(id, password, mail, org);
        this.degree = degree;
        complaints = new ArrayList<>();
    }

    public void complain(Complaint complaint) {
        Organization org1 = complaint.getComplainedOn().getOrg();
        Organization org2 = complaint.getReported().getOrg();
        if (org1.equals(org2)){
            complaint.getComplainedOn().addComplaint(complaint);
            org1.getAdmin().addComplaint(complaint);
            DBHandler.addComplaint(complaint);
        }
    }

    public void addComplaint(Complaint complaint){
        complaints.add(complaint);
    }

    public void setWarning() {
        warnings++;
        if (warnings == 3) {
            if (degree > 1) {
                degree--;
                warnings = 0;
            }else {
                status = userStatus.NotActive;
            }
        }
        DBHandler.updateUser(this);
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints.addAll(complaints);
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public void setStatus(String status) {
        this.status = userStatus.valueOf(status);
    }

    public int getDegree() {
        return degree;
    }

    public int getWarnings() {
        return warnings;
    }

    public String getStatus(){
        return status.name();
    }

    enum userStatus{ Active, NotActive, Locked }
}


