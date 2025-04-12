import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseService {
    public static void main(String[] args) {
        DatabaseService dbService = new DatabaseService();

        Admin newAdmin = new Admin(1, "John Doe", "555-1234", "securepass", 101);
        dbService.addAdmin(newAdmin);

    }

    // ADMIN OPS
    public void addAdmin(Admin admin) {
        String sql = "INSERT INTO ADMIN (admin_id, admin_name, phone_number, admin_pass, service_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters from Admin object
            pstmt.setInt(1, admin.getAdminId());
            pstmt.setString(2, admin.getAdminName());
            pstmt.setString(3, admin.getPhoneNumber());
            pstmt.setString(4, admin.getAdminPass());
            pstmt.setInt(5, admin.getServiceId());

            // Execute update
            int affectedRows = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
        }
    }
}