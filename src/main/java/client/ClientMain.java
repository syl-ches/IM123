package client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ClientMain {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientMain(String serverIP) {
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

    private void showLoginDialog() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
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
        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            writer.println("LOGIN:" + user + ":" + pass + ":" + role);

            // Handle response here (currently basic)
            JOptionPane.showMessageDialog(frame, "Login attempted as " + role);
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(loginBtn, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        String ip = JOptionPane.showInputDialog("Enter Server IP Address:");
        if (ip != null && !ip.trim().isEmpty())
            new ClientMain(ip.trim());
    }
}
