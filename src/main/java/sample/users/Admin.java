package sample.users;

import sample.actions.Complaint;
import sample.organizations.Organization;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    private List<Complaint> complaints;

    public Admin(String username, String password, String mail, Organization org) {
        super(username, password, mail, org);
        complaints = new ArrayList<>();
    }

    public void addComplaint(Complaint complaint){
        complaints.add(complaint);
        approveComplaint(complaint);
    }

    public void approveComplaint(Complaint complaint){
        complaint.approve();
        complaint.getComplainedOn().setWarning();
    }

    public void declineComplaint(Complaint complaint){
        complaint.decline();
    }

}
