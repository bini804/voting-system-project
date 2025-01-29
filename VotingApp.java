import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VotingApp {
    public static void main(String[] args) {
        new WelcomeScreen();  // Launch the welcome screen
    }
}

class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/election_system";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Error while connecting to the database.", e);
        }
    }
}

class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("Welcome to the Voting System");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set custom background image for the welcome screen
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\Students\\Desktop/vote1.jpg")); // Replace with your image path
        backgroundLabel.setLayout(new BorderLayout());

        // Logo (Placeholder for actual logo image)
        JLabel logoLabel = new JLabel(new ImageIcon("C:\\Users\\Students\\Desktop"));  // You can replace the image path
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Welcome message
        JLabel welcomeMessage = new JLabel("<html><h1 style='color:green;'>Welcome to the Voting System!</h1><br/>" +
                "Your voice matters. Please proceed to cast your vote.</html>", JLabel.CENTER);
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeMessage.setForeground(Color.WHITE);

        // Start Voting button
        JButton startButton = new JButton("Start Voting");
        startButton.setBackground(new Color(34, 139, 34));  // Green background
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VoterInfoScreen();  // Proceed to the voter information screen
                dispose();  // Close the welcome screen
            }
        });

        // Add components to the background
        backgroundLabel.add(logoLabel, BorderLayout.CENTER);
        backgroundLabel.add(welcomeMessage, BorderLayout.NORTH);
        backgroundLabel.add(startButton, BorderLayout.SOUTH);

        // Set the background as the JFrame content
        setContentPane(backgroundLabel);
        setVisible(true);
    }
}

class VoterInfoScreen extends JFrame {
    private JTextField nameField, cnicField, cityField;
    private JRadioButton maleRadio, femaleRadio;
    private JButton nextButton;
    private ButtonGroup genderGroup;

    public VoterInfoScreen() {
        setTitle("Enter Your Information");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Set custom background image for the voting interface
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\Students\\Desktop/g2.jpg"));  // Replace with your image path
        backgroundLabel.setLayout(new BorderLayout());

        // Set a semi-transparent panel to make the text more readable over the image
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(7, 2, 10, 10));
        contentPanel.setOpaque(false); // Make the content panel transparent

        // Labels and input fields with increased font size and bold
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bold and bigger font
        nameField = new JTextField(20);

        JLabel cnicLabel = new JLabel("CNIC No:");
        cnicLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bold and bigger font
        cnicField = new JTextField(15);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bold and bigger font
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Bold and bigger font
        cityField = new JTextField(20);

        nextButton = new JButton("Next");

        // Set colors for labels
        nameLabel.setForeground(new Color(0, 102, 204));
        cnicLabel.setForeground(new Color(0, 102, 204));
        genderLabel.setForeground(new Color(0, 102, 204));
        cityLabel.setForeground(new Color(0, 102, 204));

        // Customize next button
        nextButton.setBackground(new Color(34, 139, 34));  // Green button
        nextButton.setForeground(Color.WHITE);

        // Add components to the content panel
        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(cnicLabel);
        contentPanel.add(cnicField);
        contentPanel.add(genderLabel);
        contentPanel.add(maleRadio);
        contentPanel.add(new JLabel());
        contentPanel.add(femaleRadio);
        contentPanel.add(cityLabel);
        contentPanel.add(cityField);
        contentPanel.add(new JLabel());
        contentPanel.add(nextButton);

        // Action listener for the next button
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String cnic = cnicField.getText();
                String gender = maleRadio.isSelected() ? "Male" : "Female";
                String city = cityField.getText();

                if (name.isEmpty() || cnic.isEmpty() || city.isEmpty() || (!maleRadio.isSelected() && !femaleRadio.isSelected())) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                } else {
                    saveVoterData(name, cnic, gender, city);
                    new PartyVoteScreen(name, cnic, gender, city);
                    dispose();
                }
            }
        });

        // Add content panel to the background label
        backgroundLabel.add(contentPanel, BorderLayout.CENTER);

        // Set the background as the JFrame content
        setContentPane(backgroundLabel);
        setVisible(true);
    }

    private void saveVoterData(String name, String cnic, String gender, String city) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO voters_info (full_name, cnic_number, gender_type, city_name) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, cnic);
            stmt.setString(3, gender);
            stmt.setString(4, city);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class PartyVoteScreen extends JFrame {
    private String name, cnic, gender, city;
    private JButton partyAButton, partyBButton;

    public PartyVoteScreen(String name, String cnic, String gender, String city) {
        this.name = name;
        this.cnic = cnic;
        this.gender = gender;
        this.city = city;

        setTitle("Vote for Your Party");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        // Set custom background image for the voting screen
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\Students\\Desktop/vote7.jpg"));  // Replace with your image path
        backgroundLabel.setLayout(new BorderLayout());

        // Set a semi-transparent panel to make the text more readable over the image
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(4, 1, 10, 10));
        contentPanel.setOpaque(false); // Make the content panel transparent

        // Greeting label
        JLabel greetingLabel = new JLabel("Hello " + name + ", Please choose a party to vote for!", JLabel.CENTER);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        greetingLabel.setForeground(new Color(0, 102, 204));

        // Party buttons
        partyAButton = new JButton("Vote for Party X");
        partyBButton = new JButton("Vote for Party Y");

        partyAButton.setBackground(new Color(0, 123, 255));  // Blue color
        partyAButton.setForeground(Color.WHITE);
        partyBButton.setBackground(new Color(220, 53, 69));  // Red color
        partyBButton.setForeground(Color.WHITE);

        // Add components to the content panel
        contentPanel.add(greetingLabel);
        contentPanel.add(partyAButton);
        contentPanel.add(partyBButton);

        // Action listeners for voting buttons
        partyAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                castVote("Party X");
                new VoteConfirmationScreen(name, cnic, gender, city, "Party X");
                dispose();
            }
        });

        partyBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                castVote("Party Y");
                new VoteConfirmationScreen(name, cnic, gender, city, "Party Y");
                dispose();
            }
        });

        // Add content panel to the background label
        backgroundLabel.add(contentPanel, BorderLayout.CENTER);

        // Set the background as the JFrame content
        setContentPane(backgroundLabel);
        setVisible(true);
    }

    private void castVote(String party) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE vote_party_count SET party_votes = party_votes + 1 WHERE party_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, party);
            stmt.executeUpdate();

            query = "UPDATE voters_info SET selected_party = ? WHERE cnic_number = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, party);
            stmt.setString(2, cnic);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class VoteConfirmationScreen extends JFrame {
    private String name, cnic, gender, city, party;

    public VoteConfirmationScreen(String name, String cnic, String gender, String city, String party) {
        this.name = name;
        this.cnic = cnic;
        this.gender = gender;
        this.city = city;
        this.party = party;

        setTitle("Vote Confirmation");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Confirmation message
        JLabel confirmationMessage = new JLabel("<html><h2 style='color:green;'>Thank you " + name + "!</h2><br/>" +
                "You have successfully voted for " + party + ".<br/><br/>" +
                "Details:<br/>" +
                "CNIC: " + cnic + "<br/>" +
                "Gender: " + gender + "<br/>" +
                "City: " + city + "</html>", JLabel.CENTER);
        confirmationMessage.setFont(new Font("Arial", Font.PLAIN, 16));

        // Finish button
        JButton finishButton = new JButton("Finish");
        finishButton.setBackground(new Color(255, 223, 0));  // Yellow color
        finishButton.setForeground(Color.BLACK);
        finishButton.setFont(new Font("Arial", Font.BOLD, 14));
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application
            }
        });

        add(confirmationMessage, BorderLayout.CENTER);
        add(finishButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
