package requestor;

import javax.swing.*;
import java.awt.*;

public class RequestorDashboardView extends JFrame {
    private JButton viewRequestsButton;
    private JButton createRequestButton;
    private JButton logoutButton;

    public RequestorDashboardView() {
        setTitle("Requestor Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color darkGreen = new Color(0, 100, 0);
        Color brown = new Color(187, 96, 28);

        getContentPane().setBackground(darkGreen);
        setLayout(new BorderLayout());

        // Top header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(darkGreen);
        JLabel greetingLabel = new JLabel("Hello Requestor!");
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        greetingLabel.setForeground(Color.WHITE);
        headerPanel.add(greetingLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);  // transparent so background shows through

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        viewRequestsButton = new JButton("View Requests");
        createRequestButton = new JButton("Create New Request");
        logoutButton = new JButton("Logout");

        for (JButton button : new JButton[]{viewRequestsButton, createRequestButton, logoutButton}) {
            button.setBackground(brown);
            button.setForeground(Color.WHITE);
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        gbc.gridx = 0; gbc.gridy = 0;
        buttonPanel.add(viewRequestsButton, gbc);
        gbc.gridy = 1;
        buttonPanel.add(createRequestButton, gbc);
        gbc.gridy = 2;
        buttonPanel.add(logoutButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public JButton getViewRequestsButton()   { return viewRequestsButton; }
    public JButton getCreateRequestButton()  { return createRequestButton; }
    public JButton getLogoutButton()         { return logoutButton; }
}
