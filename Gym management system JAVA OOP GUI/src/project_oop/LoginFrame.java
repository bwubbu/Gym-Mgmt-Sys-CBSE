package project_oop;

import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.*;

public class LoginFrame extends javax.swing.JFrame {
    
    Admin ad;
    Gym gym;
    
    public LoginFrame() {
        initComponents();
        gym = new Gym();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnChangeCredentials = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cmbRole = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 0, 51));

        jPanel1.setBackground(new java.awt.Color(35, 43, 42));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Segoe UI", 0, 18));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Username");

        txtUserName.setBackground(new java.awt.Color(35, 43, 42));
        txtUserName.setFont(new java.awt.Font("Segoe UI", 0, 18));
        txtUserName.setForeground(new java.awt.Color(255, 255, 255));
        txtUserName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserNameActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Password");

        txtPassword.setBackground(new java.awt.Color(35, 42, 42));
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtPassword.setCaretColor(new java.awt.Color(255, 255, 255));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        btnLogin.setBackground(new java.awt.Color(17, 122, 102));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.setBorder(null);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnChangeCredentials.setBackground(new java.awt.Color(17, 122, 102));
        btnChangeCredentials.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnChangeCredentials.setForeground(new java.awt.Color(255, 255, 255));
        btnChangeCredentials.setText("Change Credentials");
        btnChangeCredentials.setBorder(null);
        btnChangeCredentials.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeCredentialsActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Role");

        cmbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Member", "Trainer" }));
        cmbRole.setBackground(new java.awt.Color(35, 43, 42));
        cmbRole.setFont(new java.awt.Font("Tahoma", 1, 14));
        cmbRole.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnChangeCredentials, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                .addGap(103, 103, 103))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserName)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChangeCredentials, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String role = (String) cmbRole.getSelectedItem();
            String userName = txtUserName.getText();
            String password = new String(txtPassword.getPassword());
            
            if (role.equals("Admin")) {
                ad = new Admin();
                boolean login = ad.validateLogin(userName, password);
                if (login) {
                    dispose();
                    JFrameMainMenu menu = new JFrameMainMenu();
                    menu.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Can't login, enter correct username and password first.");
                    txtPassword.setText("");
                    txtUserName.setText("");
                }
            } else if (role.equals("Member")) {
                // Member login: Username = MemberID, Password = PhoneNum
                if (gym.checkNumberIsInt(userName)) {
                    int memberId = Integer.parseInt(userName);
                    if (gym.memberExist(memberId)) {
                        Member member = gym.memberData(memberId);
                        if (member.phoneNum != null && member.phoneNum.equals(password)) {
                            dispose();
                            MemberDashboard memberDashboard = new MemberDashboard(memberId);
                            memberDashboard.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid password. Please enter your phone number.");
                            txtPassword.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Member not found with ID: " + userName);
                        txtPassword.setText("");
                        txtUserName.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Member ID should be a number.");
                    txtPassword.setText("");
                    txtUserName.setText("");
                }
            } else if (role.equals("Trainer")) {
                // Trainer login: Username = TrainerID, Password = PhoneNum
                if (gym.checkNumberIsInt(userName)) {
                    int trainerId = Integer.parseInt(userName);
                    if (gym.trainerExist(trainerId)) {
                        Trainer trainer = gym.trainerData(trainerId);
                        if (trainer.phoneNum != null && trainer.phoneNum.equals(password)) {
                            dispose();
                            JFrameMainMenu menu = new JFrameMainMenu();
                            menu.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid password. Please enter your phone number.");
                            txtPassword.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Trainer not found with ID: " + userName);
                        txtPassword.setText("");
                        txtUserName.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Trainer ID should be a number.");
                    txtPassword.setText("");
                    txtUserName.setText("");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error during login: " + e.getMessage());
        }
    }

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void btnChangeCredentialsActionPerformed(java.awt.event.ActionEvent evt) {
        // Only Admin can change credentials
        String role = (String) cmbRole.getSelectedItem();
        if (role != null && role.equals("Admin")) {
            dispose();
            JFrameChangeCretendials changeCred = new JFrameChangeCretendials();
            changeCred.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Only Admin can change credentials. Please select Admin role.");
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnChangeCredentials;
    private javax.swing.JButton btnLogin;
    private javax.swing.JComboBox<String> cmbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
}

