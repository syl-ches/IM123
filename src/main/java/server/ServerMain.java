package server;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;

public class ServerMain extends JFrame {
    private JButton startButton, stopButton;
    private JTextArea logArea;
    private ServerSocket serverSocket;
    private ExecutorService clientPool;
    private boolean isRunning = false;
    private Connection dbConnection;

    public ServerMain() {
        setTitle("Java Server");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");
        stopButton.setEnabled(false);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startServer());
        stopButton.addActionListener(e -> stopServer());
    }

    private void startServer() {
        try {
            dbConnection = DatabaseConnection.getConnection();
            log("Database connected successfully.");

            serverSocket = new ServerSocket(2000);
            clientPool = Executors.newFixedThreadPool(10);
            isRunning = true;

            startButton.setEnabled(false);
            stopButton.setEnabled(true);

            String localIP = InetAddress.getLocalHost().getHostAddress();
            log("Server started on port 2000.");
            log("Server IP address: " + localIP);

            new Thread(() -> {
                while (isRunning) {
                    try {
                        Socket client = serverSocket.accept();
                        log("Client connected: " + client.getInetAddress());
                        clientPool.execute(new ClientHandler(client));
                    } catch (IOException ex) {
                        if (isRunning) log("Error accepting client: " + ex.getMessage());
                    }
                }
            }).start();

        } catch (SQLException e) {
            log("Database connection failed: " + e.getMessage());
        } catch (IOException e) {
            log("Could not start server: " + e.getMessage());
        }
    }

    private void stopServer() {
        isRunning = false;
        try {
            if (serverSocket != null) serverSocket.close();
            if (clientPool != null) clientPool.shutdownNow();
            if (dbConnection != null) DatabaseConnection.close(dbConnection);
            log("Server stopped.");
        } catch (IOException e) {
            log("Error stopping server: " + e.getMessage());
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> logArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServerMain().setVisible(true));
    }

    class ClientHandler implements Runnable {
        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                writer.println("Connected to server!");
                String line;
                while ((line = reader.readLine()) != null) {
                    log("Received: " + line);
                    writer.println("Echo: " + line);
                }
            } catch (IOException e) {
                log("Client disconnected.");
            }
        }
    }
}
