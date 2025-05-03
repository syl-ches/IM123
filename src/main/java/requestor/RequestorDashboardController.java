package requestor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RequestorDashboardController {
    private RequestorDashboardModel model;
    private RequestorDashboardView view;

    public RequestorDashboardController(RequestorDashboardModel model, RequestorDashboardView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.getViewRequestsButton().addActionListener(e -> onViewRequests());
        view.getCreateRequestButton().addActionListener(e -> onCreateRequest());
        view.getLogoutButton().addActionListener(e -> onLogout());
    }

    private void onViewRequests() {
        String input = JOptionPane.showInputDialog(view, "Enter your Requestor ID: (should be automatic, but will update when populated");
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Requestor ID is required.");
            return;
        }

        try {
            int requestorId = Integer.parseInt(input.trim());

            // DB credentials
            String url = "jdbc:mysql://localhost:3306/monami?useSSL=false";
            String user = "root";
            String password = "NLGPz,MARKS27&&";

            // Connect and query
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                String query = "SELECT service_id, service_location, service_type, start_date, end_date, current_status " +
                        "FROM SERVICE WHERE volunteer_id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, requestorId);
                ResultSet rs = stmt.executeQuery();

                StringBuilder sb = new StringBuilder();
                while (rs.next()) {
                    sb.append("Service ID: ").append(rs.getInt("service_id")).append("\n")
                            .append("Location: ").append(rs.getString("service_location")).append("\n")
                            .append("Type: ").append(rs.getString("service_type")).append("\n")
                            .append("Start: ").append(rs.getDate("start_date")).append("\n")
                            .append("End: ").append(rs.getDate("end_date")).append("\n")
                            .append("Status: ").append(rs.getString("current_status")).append("\n\n");
                }

                if (sb.length() == 0) {
                    JOptionPane.showMessageDialog(view, "No requests found for requestor ID " + requestorId);
                } else {
                    JTextArea area = new JTextArea(sb.toString());
                    area.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(area);
                    scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
                    JOptionPane.showMessageDialog(view, scrollPane, "Requests", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Invalid ID format.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Database error: " + ex.getMessage());
        }
    }

    private void onCreateRequest() {
        JOptionPane.showMessageDialog(view, "Create New Request clicked");
    }

    private void onLogout() {
        view.dispose();
        client.ClientMain.reopenLogin(); // Re-show login without re-entering IP
    }
}
