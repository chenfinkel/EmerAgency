package sample;

import sample.actions.Complaint;
import sample.actions.Event;
import sample.actions.Update;
import sample.organizations.Organization;
import sample.organizations.ServiceCenter;
import sample.users.Admin;
import sample.users.BasicUser;
import sample.users.SecurityUser;
import sample.users.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBHandler {

    private static String url = "jdbc:sqlite:EmerAgency.db";

    public static void addEvent(Event event){
        int newEventID = getMaxID("events", "eventID") + 1;
        int updateID = addUpdate(event.getLastUpdate(), newEventID, -1);
        String query = "INSERT INTO events(Date, Status, currentUpdate, Title) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, event.getDate().toString());
            pstmt.setString(2, event.getStatus());
            pstmt.setInt(3, updateID);
            pstmt.setString(4, event.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setEventCategories(newEventID, event);
        setEventSecurity(newEventID, event);
    }

    /*public static void setUpdate(Event event){
        int eventID = -1;
        int previousID = -1;
        String query = "SELECT eventID FROM events WHERE Date = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, event.getDate().toString());
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                eventID = rs.getInt("eventID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        query = "SELECT updateID FROM Updates WHERE Date = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, event.getLastUpdate().getPrevious().getDate().toString());
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                previousID = rs.getInt("updateID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int updateID = addUpdate(event.getLastUpdate(), eventID, previousID);

        query = "UPDATE"
    }*/

    public static void addComplaint(Complaint complaint){
        String reported = complaint.getReported().getUsername();
        String complainedOn = complaint.getComplainedOn().getUsername();
        String query = "INSERT INTO Complaints (Reported, ComplainedOn, Description, Status) VALUES (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, reported);
            pstmt.setString(2, complainedOn);
            pstmt.setString(3, complaint.getDescription());
            pstmt.setString(4, complaint.getStatus());
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEvent(Update update){
        Event event = update.getEvent();
        String Date = event.getDate().toString();
        int eventID = getID(Date, "events", "eventID", "Date");
        int oldUpdateID = getID(Date, "Updates", "updateID", "Date");
        int newUpdateID = addUpdate(update, eventID, oldUpdateID);

        String query = "UPDATE events SET currentUpdate = ?" + " WHERE eventID = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, newUpdateID);
            pstmt.setInt(2, eventID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static List<String> getCategories(){
        List<String> categories = new ArrayList<>();
        String query = "SELECT Name FROM Categories";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("Name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return categories;
    }

    public static List<BasicUser> getUsersByOrg(Organization org){
        List<BasicUser> users = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE Organization = ? AND Admin = 0";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            String organization = org.getClass().toString().split(" sample.organizations.")[1];
            pstmt.setString(1,organization);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                /*String password = rs.getString("Password");
                String mail = rs.getString("Mail");
                int degree = rs.getInt("Degree");
                int warnings = rs.getInt("Warnings");
                String status = rs.getString("Status");
                users.add(org.createUser(username,password,mail,org,degree));*/
                users.add(getUser(username));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    private static void setEventCategories(int newEventID, Event event){
        String query = "INSERT INTO eventCategories(eventID, Category) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            List<String> categories = event.getCategories();
            for (int i = 0; i < categories.size(); i++) {
                pstmt.setInt(1, newEventID);
                pstmt.setString(2, categories.get(i));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setEventSecurity(int newEventID, Event event){
        String query = "INSERT INTO eventSecurity(eventID, Username) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            List<SecurityUser> security = event.getSecurity();
            for (int i = 0; i < security.size(); i++) {
                pstmt.setInt(1, newEventID);
                String username = security.get(i).getUsername();
                pstmt.setString(2, username);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getID(String name, String table, String IDColumnLabel, String nameColumnLabel){
        String query = "SELECT " + IDColumnLabel+ " FROM " + table + " WHERE " + nameColumnLabel + " = ? ";
        int id = -1;
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setString(1,name);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt(IDColumnLabel);
            }
        } catch (Exception e){e.printStackTrace();}
        return id;
    }

    private static int addUpdate(Update update, int eventID, int prev){
        String query = "INSERT INTO Updates(currentDescription, originalDescription, eventID, Date, Previous) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, update.getCurrentDescription());
            pstmt.setString(2, update.getOriginalDescription());
            pstmt.setInt(3, eventID);
            pstmt.setString(4, update.getDate().toString());
            pstmt.setInt(5, prev);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        return getMaxID("Updates","updateID");
    }

    private static int getMaxID(String table, String columnLabel){
        int ID = -1;
        String query = "SELECT MAX("+ columnLabel + ") FROM "+ table +"";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                ID = rs.getInt("MAX("+columnLabel+")");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ID;
    }

    public static Admin getAdmin(Organization org) {
        Admin admin = null;
        String query = "SELECT * FROM Users WHERE Organization = ? AND Admin = 1";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setString(1,org.getClass().toString().split(" sample.organizations.")[1]);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String mail = rs.getString("Mail");
                admin = new Admin(username,password,mail,org);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return admin;
    }

    public static Event getEvent(String title) {
        Event event = null;
        String query = "SELECT * FROM events WHERE Title = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setString(1,title);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String Date = rs.getString("Date");
                LocalDateTime lclDT = LocalDateTime.parse(Date);
                String status = rs.getString("Status");
                int eventID = rs.getInt("eventID");
                List<BasicUser> users = getEventUsers(eventID);
                int updateID = rs.getInt("currentUpdate");
                Update update = getUpdate(updateID);
                List<String> categories = getEventCategories(eventID);
                event = new Event(title, users, update, categories, status);
                update.setEvent(event);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return event;
    }

    private static List<String> getEventCategories(int eventID) {
        List<String> categories = new ArrayList<>();
        String query = "SELECT * FROM eventCategories WHERE eventID = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setInt(1,eventID);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String category = rs.getString("Category");
                categories.add(category);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return categories;
    }

    private static Update getUpdate(int updateID) {
        Update update = null;
        String query = "SELECT * FROM Updates WHERE updateID = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setInt(1,updateID);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String Date = rs.getString("Date");
                LocalDateTime lclDT = LocalDateTime.parse(Date);
                String current = rs.getString("currentDescription");
                String original = rs.getString("originalDescription");
                update = new Update();
                update.setCurrentDescription(current);
                update.setDate(lclDT);
                update.setOriginalDescription(original);
                int prev = rs.getInt("Previous");
                if (prev != -1) {
                    update.setPrevious(getUpdate(prev));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return update;
    }

    private static List<BasicUser> getEventUsers(int eventID) {
        List<BasicUser> users = new ArrayList<>();
        String query = "SELECT * FROM eventSecurity WHERE eventID = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setInt(1,eventID);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                users.add(getUser(username));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public static BasicUser login(String username){
        return getUser(username);
    }

    private static BasicUser getUser(String username) {
        BasicUser user = null;
        String query = "SELECT * FROM Users WHERE Username = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setString(1,username);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String password = rs.getString("Password");
                String mail = rs.getString("Mail");
                int degree = rs.getInt("Degree");
                String org = rs.getString("Organization");
                int warnings = rs.getInt("Warnings");
                String status = rs.getString("Status");
                Organization org2 = ServiceCenter.getInstance().getSecurityOrgs().get(org);
                user = org2.createUser(username,password,mail,org2,degree);
                user.setWarnings(warnings);
                user.setStatus(status);
                List<Complaint> complaints = getComplaints(user);
                user.setComplaints(complaints);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    private static List<Complaint> getComplaints(BasicUser user) {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM Complaints WHERE complainedOn = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setString(1,user.getUsername());
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String reported = rs.getString("Reported");
                BasicUser userReported = getUser(reported);
                String description = rs.getString("Description");
                String status = rs.getString("Status");
                Complaint c = new Complaint(userReported, user, description);
                c.setStatus(status);
                complaints.add(c);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return complaints;
    }

    public static void updateUser(BasicUser basicUser) {
        String query = "UPDATE Users SET Degree = ? , Warnings = ? , Status = ? WHERE Username = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, basicUser.getDegree());
            pstmt.setInt(2, basicUser.getWarnings());
            pstmt.setString(3, basicUser.getStatus());
            pstmt.setString(4, basicUser.getUsername());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
