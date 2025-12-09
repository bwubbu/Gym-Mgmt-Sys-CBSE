package project_oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberDashboard extends javax.swing.JFrame {
    
    private Gym gym;
    private int memberId;
    private Member currentMember;
    private JPanel mainPanel;
    private JButton btnMyProfile, btnBookMachine, btnMySchedule, btnMyHistory, btnLogout;
    private JTextArea displayArea;
    private JScrollPane scrollPane;
    
    public MemberDashboard(int memberId) {
        this.memberId = memberId;
        gym = new Gym();
        if (gym.memberExist(memberId)) {
            currentMember = gym.memberData(memberId);
        }
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Member Dashboard - " + (currentMember != null ? currentMember.name : "Member"));

        mainPanel = new JPanel();
        mainPanel.setBackground(new java.awt.Color(35, 43, 42));
        mainPanel.setLayout(new BorderLayout());

        // Top panel with buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new java.awt.Color(35, 43, 42));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnMyProfile = new JButton("My Profile");
        btnMyProfile.setBackground(new java.awt.Color(17, 122, 102));
        btnMyProfile.setForeground(Color.WHITE);
        btnMyProfile.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnMyProfile.setPreferredSize(new Dimension(150, 40));
        btnMyProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMyProfile();
            }
        });

        btnBookMachine = new JButton("Book Machine");
        btnBookMachine.setBackground(new java.awt.Color(17, 122, 102));
        btnBookMachine.setForeground(Color.WHITE);
        btnBookMachine.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBookMachine.setPreferredSize(new Dimension(150, 40));
        btnBookMachine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showBookMachine();
            }
        });

        btnMySchedule = new JButton("My Schedule");
        btnMySchedule.setBackground(new java.awt.Color(17, 122, 102));
        btnMySchedule.setForeground(Color.WHITE);
        btnMySchedule.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnMySchedule.setPreferredSize(new Dimension(150, 40));
        btnMySchedule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMySchedule();
            }
        });

        btnMyHistory = new JButton("My History");
        btnMyHistory.setBackground(new java.awt.Color(17, 122, 102));
        btnMyHistory.setForeground(Color.WHITE);
        btnMyHistory.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnMyHistory.setPreferredSize(new Dimension(150, 40));
        btnMyHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMyHistory();
            }
        });

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new java.awt.Color(200, 50, 50));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnLogout.setPreferredSize(new Dimension(150, 40));
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        buttonPanel.add(btnMyProfile);
        buttonPanel.add(btnBookMachine);
        buttonPanel.add(btnMySchedule);
        buttonPanel.add(btnMyHistory);
        buttonPanel.add(btnLogout);

        // Display area
        displayArea = new JTextArea();
        displayArea.setBackground(new java.awt.Color(35, 43, 42));
        displayArea.setForeground(Color.WHITE);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Welcome, " + (currentMember != null ? currentMember.name : "Member") + "!");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Show welcome message
        displayArea.setText("Welcome to the Member Dashboard!\n\n" +
                           "Select an option from the menu above to get started.\n\n" +
                           "1. My Profile - View and edit your body stats\n" +
                           "2. Book Machine - Book a gym machine\n" +
                           "3. My Schedule - View your active bookings\n" +
                           "4. My History - View your payment receipts");
    }

    private void showMyProfile() {
        if (currentMember == null) {
            displayArea.setText("Error: Member data not found.");
            return;
        }

        // Create a dialog for viewing/editing profile
        JDialog profileDialog = new JDialog(this, "My Profile", true);
        profileDialog.setSize(500, 400);
        profileDialog.setLocationRelativeTo(this);
        profileDialog.getContentPane().setBackground(new java.awt.Color(35, 43, 42));

        JPanel profilePanel = new JPanel();
        profilePanel.setBackground(new java.awt.Color(35, 43, 42));
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Display current info
        JTextArea infoArea = new JTextArea();
        infoArea.setBackground(new java.awt.Color(35, 43, 42));
        infoArea.setForeground(Color.WHITE);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        infoArea.setEditable(false);
        infoArea.setText("Current Profile Information:\n\n" +
                        "Name: " + currentMember.name + "\n" +
                        "Member ID: " + currentMember.regId + "\n" +
                        "Email: " + currentMember.gmail + "\n" +
                        "Phone: " + currentMember.phoneNum + "\n" +
                        "Height: " + currentMember.getHeight() + " feet\n" +
                        "Weight: " + currentMember.getWeight() + " kg\n" +
                        "BMI: " + String.format("%.2f", currentMember.getBmi()) + "\n" +
                        "Fitness Goal: " + currentMember.getFitnessGoal());

        JScrollPane infoScroll = new JScrollPane(infoArea);
        infoScroll.setPreferredSize(new Dimension(450, 200));

        // Edit fields
        JPanel editPanel = new JPanel();
        editPanel.setBackground(new java.awt.Color(35, 43, 42));
        editPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel heightLabel = new JLabel("Height (feet):");
        heightLabel.setForeground(Color.WHITE);
        JTextField heightField = new JTextField(String.valueOf(currentMember.getHeight()));
        heightField.setBackground(new java.awt.Color(35, 43, 42));
        heightField.setForeground(Color.WHITE);
        heightField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        JLabel weightLabel = new JLabel("Weight (kg):");
        weightLabel.setForeground(Color.WHITE);
        JTextField weightField = new JTextField(String.valueOf(currentMember.getWeight()));
        weightField.setBackground(new java.awt.Color(35, 43, 42));
        weightField.setForeground(Color.WHITE);
        weightField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        editPanel.add(heightLabel);
        editPanel.add(heightField);
        editPanel.add(weightLabel);
        editPanel.add(weightField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new java.awt.Color(17, 122, 102));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String heightStr = heightField.getText();
                String weightStr = weightField.getText();

                boolean valid = true;
                if (!heightStr.isEmpty() && currentMember.validateHeight(heightStr)) {
                    currentMember.setHeight(Double.parseDouble(heightStr));
                } else if (!heightStr.isEmpty()) {
                    JOptionPane.showMessageDialog(profileDialog, "Invalid height. Must be between 0 and 10 feet.");
                    valid = false;
                }

                if (!weightStr.isEmpty() && currentMember.validateWeight(weightStr)) {
                    currentMember.setWeight(Double.parseDouble(weightStr));
                } else if (!weightStr.isEmpty()) {
                    JOptionPane.showMessageDialog(profileDialog, "Invalid weight. Must be between 0 and 300 kg.");
                    valid = false;
                }

                if (valid) {
                    gym.modifyMember(currentMember, memberId);
                    JOptionPane.showMessageDialog(profileDialog, "Profile updated successfully!");
                    profileDialog.dispose();
                    showMyProfile(); // Refresh
                }
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new java.awt.Color(100, 100, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                profileDialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new java.awt.Color(35, 43, 42));
        buttonPanel.add(saveButton);
        buttonPanel.add(closeButton);

        profilePanel.add(infoScroll);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(editPanel);
        profilePanel.add(Box.createVerticalStrut(10));
        profilePanel.add(buttonPanel);

        profileDialog.getContentPane().add(profilePanel);
        profileDialog.setVisible(true);
    }

    private void showBookMachine() {
        String allMachines = gym.viewAllMachines();
        if (allMachines.isEmpty()) {
            displayArea.setText("No machines available.");
            return;
        }

        // Create booking dialog
        JDialog bookingDialog = new JDialog(this, "Book Machine", true);
        bookingDialog.setSize(600, 500);
        bookingDialog.setLocationRelativeTo(this);
        bookingDialog.getContentPane().setBackground(new java.awt.Color(35, 43, 42));

        JPanel bookingPanel = new JPanel();
        bookingPanel.setBackground(new java.awt.Color(35, 43, 42));
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS));
        bookingPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Available Machines:");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        JTextArea machineList = new JTextArea(allMachines);
        machineList.setBackground(new java.awt.Color(35, 43, 42));
        machineList.setForeground(Color.WHITE);
        machineList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        machineList.setEditable(false);
        JScrollPane machineScroll = new JScrollPane(machineList);
        machineScroll.setPreferredSize(new Dimension(550, 200));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new java.awt.Color(35, 43, 42));
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel machineIdLabel = new JLabel("Machine Reg ID:");
        machineIdLabel.setForeground(Color.WHITE);
        JTextField machineIdField = new JTextField();
        machineIdField.setBackground(new java.awt.Color(35, 43, 42));
        machineIdField.setForeground(Color.WHITE);
        machineIdField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        inputPanel.add(machineIdLabel);
        inputPanel.add(machineIdField);
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(new JLabel()); // Empty cell

        JButton bookButton = new JButton("Book Machine");
        bookButton.setBackground(new java.awt.Color(17, 122, 102));
        bookButton.setForeground(Color.WHITE);
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String machineIdStr = machineIdField.getText();
                if (machineIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(bookingDialog, "Please enter a Machine Reg ID.");
                    return;
                }

                if (!gym.checkNumberIsInt(machineIdStr)) {
                    JOptionPane.showMessageDialog(bookingDialog, "Machine ID must be a number.");
                    return;
                }

                int machineId = Integer.parseInt(machineIdStr);
                Machine machine = null;
                for (Machine m : gym.getMachines()) {
                    if (m.getRegId() == machineId) {
                        machine = m;
                        break;
                    }
                }

                if (machine == null) {
                    JOptionPane.showMessageDialog(bookingDialog, "Machine not found with ID: " + machineId);
                    return;
                }

                String result = machine.bookMachine(currentMember);
                if (result.equals("booked")) {
                    gym.saveMachineFile();
                    JOptionPane.showMessageDialog(bookingDialog, "Machine booked successfully!");
                    bookingDialog.dispose();
                    showMySchedule(); // Show updated schedule
                } else {
                    JOptionPane.showMessageDialog(bookingDialog, result);
                }
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new java.awt.Color(100, 100, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bookingDialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new java.awt.Color(35, 43, 42));
        buttonPanel.add(bookButton);
        buttonPanel.add(closeButton);

        bookingPanel.add(titleLabel);
        bookingPanel.add(Box.createVerticalStrut(10));
        bookingPanel.add(machineScroll);
        bookingPanel.add(Box.createVerticalStrut(10));
        bookingPanel.add(inputPanel);
        bookingPanel.add(Box.createVerticalStrut(10));
        bookingPanel.add(buttonPanel);

        bookingDialog.getContentPane().add(bookingPanel);
        bookingDialog.setVisible(true);
    }

    private void showMySchedule() {
        String bookings = gym.getMemberBookings(memberId);
        displayArea.setText("MY ACTIVE BOOKINGS\n" +
                           "==================\n\n" + bookings);
    }

    private void showMyHistory() {
        String history = gym.getMemberPaymentHistory(memberId);
        displayArea.setText("PAYMENT HISTORY & RECEIPT\n" +
                           "========================\n\n" + history);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Logout", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // For testing - requires a valid member ID
                // new MemberDashboard(1001).setVisible(true);
            }
        });
    }
}

