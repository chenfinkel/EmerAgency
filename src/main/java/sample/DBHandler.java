package sample;

import java.sql.*;
import java.util.List;

public class DBHandler {

    private static String url = "jdbc:sqlite:EmerAgency.db";

    public static boolean addEvent(Event event){
        int newEventID = getMaxID("events", "eventID") + 1;
        addUpdate(event.getLastUpdate(), newEventID);
        int updateID = getMaxID("Updates","updateID");
        String query = "INSERT INTO events(Date, Status, currentUpdate) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, event.getDate().toString());
            pstmt.setString(2, event.getStatus());
            pstmt.setInt(3, updateID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        query = "INSERT INTO eventCategories(eventID, categoryID) VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            List<Category> categories = event.getCategories();
            for (int i = 0; i < categories.size(); i++) {
                pstmt.setInt(1, newEventID);
                int catID = getCategoryId(categories.get(i).getTheme());
                pstmt.setInt(2, catID);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static int getCategoryId(String name){
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
    }

    private static boolean addUpdate(Update update, int eventID){
        String query = "INSERT INTO Updates(currentDescription, originalDescription, eventID, Date) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, update.getCurrentDescription());
            pstmt.setString(2, update.getOriginalDescription());
            pstmt.setInt(3, eventID);
            pstmt.setString(2, update.getDate().toString());
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int getMaxID(String table, String columnLabel){
        int ID = -1;
        String query = "SELECT * FROM "+ table +"ORDER BY eventID DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(query);){
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString(columnLabel);
                ID = Integer.parseInt(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ID;
    }
}
