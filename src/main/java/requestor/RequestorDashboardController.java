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
        String input = JOptionPane.showInputDialog(view, "Enter your Requestor ID:");
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Requestor ID is required.");
            return;
        }

        try {
            int requestorId = Integer.parseInt(input.trim());
            java.util.List<String> requests = model.getRequestsByRequestorId(requestorId);

            if (requests.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No requests found for requestor ID " + requestorId);
            } else {
                JTextArea area = new JTextArea(String.join("\n--------------------------\n", requests));
                area.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(area);
                scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
                JOptionPane.showMessageDialog(view, scrollPane, "Requests", JOptionPane.INFORMATION_MESSAGE);
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
