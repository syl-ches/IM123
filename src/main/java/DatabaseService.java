import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

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

            //checker cuz dependency chain
            if (!serviceExists(conn, admin.getServiceId())){
                throw new Exception(
                        "Service ID " + admin.getServiceId() + " does not exist"
                );
            }

            if (!existsVolunteerService(conn, admin.getServiceId())){
                throw new Exception(
                        "Service ID " + admin.getServiceId() + " does not exist"
                );
            }

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean serviceExists(Connection conn, int serviceId) throws SQLException{
        String sql = "SELECT 1 from VOLUNTEER_SERVICE WHERE service_id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,serviceId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean existsVolunteerService(Connection conn, int serviceId) throws SQLException {
        String sql =
                "SELECT vs.volunteer_id " +
                        "  FROM VOLUNTEER_SERVICE vs " +
                        " WHERE vs.service_id = ? " +
                        "   AND EXISTS (SELECT 1 FROM VOLUNTEER v WHERE v.volunteer_id = vs.volunteer_id)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}