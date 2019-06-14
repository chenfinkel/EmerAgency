package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        setEventOrganizations(newEventID, event);
    }

    public static void addComplaint(Complaint complaint){
        int reportedID = getUserID(complaint.getReported().getUsername());
        int complainedOnID = getUserID(complaint.getComplainedOn().getUsername());
        String query = "INSERT INTO Complaints (Reported, ComplainedOn, Description, Status) VALUES (?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, reportedID);
            pstmt.setInt(2, complainedOnID);
            pstmt.setString(3, complaint.getDescription());
            pstmt.setString(4, complaint.getStatus());
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEvent(Update update){
        Event event = update.getEvent();
        String eventTitle = event.getTitle();
        int eventID = getID(eventTitle, "events", "eventID", "Title");
        String updateName = event.getLastUpdate().getOriginalDescription();
        int oldUpdateID = getID(updateName, "Updates", "updateID", "originalDescription");
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

    public static List<User> getUsersByOrg(Organization org){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE Organization = ? AND Admin = 0";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setString(1,org.getClass().toString().split(" ")[1]);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String mail = rs.getString("Mail");
                int degree = rs.getInt("Degree");
                users.add(new BasicUser(username,password,mail,org,degree));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    private static int getUserID(String username){
        int ID = -1;
        String query = "SELECT userID FROM Users WHERE Username = ? ";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            pstmt.setString(1,username);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                ID = rs.getInt("userID");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ID;
    }

    private static void setEventCategories(int newEventID, Event event){
        String query = "INSERT INTO eventCategories(eventID, categoryID) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            List<String> categories = event.getCategories();
            for (int i = 0; i < categories.size(); i++) {
                pstmt.setInt(1, newEventID);
                //int catID = getCategoryID(categories.get(i).getTheme());
                int catID = getID(categories.get(i), "Categories", "categoryID", "Name");
                pstmt.setInt(2, catID);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setEventOrganizations(int newEventID, Event event){
        String query = "INSERT INTO eventOrganizations(eventID, Organization) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            List<Organization> orgs = event.getOrganizations();
            for (int i = 0; i < orgs.size(); i++) {
                pstmt.setInt(1, newEventID);
                String org = orgs.get(i).getClass().toString().split(" ")[1];
                pstmt.setString(2, org);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*private static int getCategoryID(String name){
        String query = "SELECT categoryID FROM Categories WHERE Name = ? ";
        int id = -1;
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);) {
            pstmt.setString(1,name);
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("categoryID");
            }
        } catch (Exception e){e.printStackTrace();}
        return id;
    }*/

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
        String query = "SELECT * FROM "+ table +"ORDER BY eventID DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                ID = rs.getInt(columnLabel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ID;
    }
}
