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
    // volunteer service and volunteer creation
    /*
    because of dependency chains, certain rules must be followed so that queries actually work
    1. admins cannot exist without volunteer service records
    2. volunteer service cannot exist without at least one volunteer to reference
    3. upon the creation of a volunteer_service for the first time, the field "volunteer_id" must be null unless there exists a volunteer to reference for the have an id and that is an FK

    SAMPLE QUERY SEQUENCING WHEN A volunteer_service RECORD HAS BEEN MADE WITH NO EXISTING VOLUNTEERS
    INSERT INTO volunteer_service (service_id, service_location, service_type, start_date, end_date, current_status, volunteer_id) VALUES (1,"O Block","Saint Von Memorial", 2025-03-11,2025-03-13,"active",null);

    INSERT INTO volunteer(volunteer_id, volunteer_name, phone_number, volunteer_email, volunteer_pass ) VALUES (1,"Reimu Hakurei","09999998888","ReimuuuHakurei@gmail.com","Pichuun");

    UPDATE volunteer_service SET volunteer_id = 1 WHERE service_id = 1;
     */


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