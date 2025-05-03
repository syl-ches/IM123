package client;

import requestor.RequestorDashboardController;
import requestor.RequestorDashboardModel;
import requestor.RequestorDashboardView;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientMain {
    private static ClientMain instance;

    private final String serverIP;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientMain(String serverIP) {
        this.serverIP = serverIP;
        instance = this;

        try {
            socket = new Socket(serverIP, 2000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            String welcome = reader.readLine();
            JOptionPane.showMessageDialog(null, welcome);

            showLoginDialog();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server: " + e.getMessage());
        }
    }


    public static void reopenLogin() {
        if (instance != null) {
            SwingUtilities.invokeLater(instance::showLoginDialog);
        }
    }

    private void showLoginDialog() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Admin", "Volunteer", "Requestor"});

        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel("Role:"));
        panel.add(roleBox);

        JButton loginBtn = new JButton("Login");
        panel.add(new JLabel());
        panel.add(loginBtn);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            String role = ((String) roleBox.getSelectedItem()).toLowerCase();

            writer.println("LOGIN:" + user + ":" + pass + ":" + role);

            if ("requestor".equals(role)) {
                SwingUtilities.invokeLater(() -> {
                    RequestorDashboardModel m = new RequestorDashboardModel();
                    RequestorDashboardView  v = new RequestorDashboardView();
                    new RequestorDashboardController(m, v);
                    v.setVisible(true);
                });
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Unauthorized role: " + role);
            }
        });
    }

    public static void main(String[] args) {
        String ip = JOptionPane.showInputDialog("Enter Server IP Address:");
        if (ip != null && !ip.trim().isEmpty()) {
            new ClientMain(ip.trim());
        }
    }
}
