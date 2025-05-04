package requestor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestorDashboardModel {
    private static final String URL = "jdbc:mysql://localhost:3306/monami?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public List<String> getRequestsByRequestorId(int requestorId) throws SQLException {
        List<String> requests = new ArrayList<>();

        // Use GROUP_CONCAT to aggregate beneficiary names and phone numbers for each service
        String query = "SELECT s.service_id, s.service_location, s.service_type, s.start_date, s.end_date, s.current_status, " +
                "GROUP_CONCAT(b.beneficiary_name ORDER BY b.beneficiary_name) AS beneficiaries, " +
                "GROUP_CONCAT(b.phone_number ORDER BY b.beneficiary_name) AS phones " +
                "FROM SERVICE s " +
                "LEFT JOIN SERVICE_TARGET st ON s.service_id = st.service_id " +
                "LEFT JOIN BENEFICIARY b ON st.beneficiary_id = b.beneficiary_id " +
                "WHERE s.volunteer_id = ? " +
                "GROUP BY s.service_id, s.service_location, s.service_type, s.start_date, s.end_date, s.current_status";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, requestorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Get concatenated list of beneficiaries and their phone numbers
                String beneficiaries = rs.getString("beneficiaries");
                String phones = rs.getString("phones");

                // Format request information
                String request = String.format(
                        "Service ID: %d\nLocation: %s\nType: %s\nStart: %s\nEnd: %s\nStatus: %s\n" +
                                "Beneficiaries: %s\nPhone Numbers: %s\n",
                        rs.getInt("service_id"),
                        rs.getString("service_location"),
                        rs.getString("service_type"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("current_status"),
                        beneficiaries != null ? beneficiaries : "N/A",  // If no beneficiaries, display "N/A"
                        phones != null ? phones : "N/A"  // If no phone numbers, display "N/A"
                );
                requests.add(request);
            }
        }

        return requests;
    }
}
