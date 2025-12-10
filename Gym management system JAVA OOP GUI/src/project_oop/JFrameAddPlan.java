package project_oop;
import javax.swing.*;
public class JFrameAddPlan extends javax.swing.JFrame {

    Gym gym = new Gym();
    MemberPlan plan = new MemberPlan();
    public JFrameAddPlan(){
        initComponents();
        txtPlanId.setEditable(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPlanId = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnGeneratePlanId = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtPlanName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPlanDuration = new javax.swing.JList<>();
        jLabel16 = new javax.swing.JLabel();
        txtPlanPrice = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPlanDescription = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        chkAccessToAllMachines = new javax.swing.JCheckBox();
        chkPersonalTrainer = new javax.swing.JCheckBox();
        chkGroupClasses = new javax.swing.JCheckBox();
        chkLockerAccess = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(35, 43, 42));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Add Member Plan");

        txtPlanId.setBackground(new java.awt.Color(35, 43, 42));
        txtPlanId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPlanId.setForeground(new java.awt.Color(255, 255, 255));
        txtPlanId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtPlanId.setMargin(new java.awt.Insets(2, 1, 2, 1));
        txtPlanId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanIdActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Plan ID:");

        btnGeneratePlanId.setBackground(new java.awt.Color(17, 122, 102));
        btnGeneratePlanId.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGeneratePlanId.setForeground(new java.awt.Color(255, 255, 255));
        btnGeneratePlanId.setText("GENERATE NUMBER");
        btnGeneratePlanId.setBorder(null);
        btnGeneratePlanId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneratePlanIdActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Plan Name:");

        txtPlanName.setBackground(new java.awt.Color(35, 43, 42));
        txtPlanName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPlanName.setForeground(new java.awt.Color(255, 255, 255));
        txtPlanName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtPlanName.setMargin(new java.awt.Insets(2, 1, 2, 1));
        txtPlanName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanNameActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Plan Duration:");

        listPlanDuration.setBackground(new java.awt.Color(35, 43, 42));
        listPlanDuration.setForeground(new java.awt.Color(255, 255, 255));
        listPlanDuration.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Select", "Monthly", "Quarterly", "Annual" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listPlanDuration.setSelectedIndex(0);
        jScrollPane1.setViewportView(listPlanDuration);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Plan Price:");

        txtPlanPrice.setBackground(new java.awt.Color(35, 43, 42));
        txtPlanPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPlanPrice.setForeground(new java.awt.Color(255, 255, 255));
        txtPlanPrice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtPlanPrice.setMargin(new java.awt.Insets(2, 1, 2, 1));
        txtPlanPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanPriceActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Plan Description:");

        txtPlanDescription.setBackground(new java.awt.Color(35, 43, 42));
        txtPlanDescription.setColumns(20);
        txtPlanDescription.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtPlanDescription.setForeground(new java.awt.Color(255, 255, 255));
        txtPlanDescription.setRows(5);
        txtPlanDescription.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        jScrollPane2.setViewportView(txtPlanDescription);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Plan Features:");

        chkAccessToAllMachines.setForeground(new java.awt.Color(255, 255, 255));
        chkAccessToAllMachines.setText("Access to all machines");

        chkPersonalTrainer.setForeground(new java.awt.Color(255, 255, 255));
        chkPersonalTrainer.setText("Personal trainer included");

        chkGroupClasses.setForeground(new java.awt.Color(255, 255, 255));
        chkGroupClasses.setText("Group classes included");

        chkLockerAccess.setForeground(new java.awt.Color(255, 255, 255));
        chkLockerAccess.setText("Locker access");

        btnSave.setBackground(new java.awt.Color(17, 122, 102));
        btnSave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("SAVE");
        btnSave.setBorder(null);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(17, 122, 102));
        btnBack.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.setBorder(null);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPlanId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGeneratePlanId, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPlanName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPlanPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkAccessToAllMachines)
                                    .addComponent(chkPersonalTrainer)
                                    .addComponent(chkGroupClasses)
                                    .addComponent(chkLockerAccess))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlanId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGeneratePlanId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlanName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlanPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkAccessToAllMachines)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkPersonalTrainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkGroupClasses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkLockerAccess)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPlanIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanIdActionPerformed
        int planId = gym.generateAutoRegIdForPlan();
        txtPlanId.setText(String.valueOf(planId));
    }//GEN-LAST:event_txtPlanIdActionPerformed

    private void btnGeneratePlanIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneratePlanIdActionPerformed
        txtPlanIdActionPerformed(evt);
    }//GEN-LAST:event_btnGeneratePlanIdActionPerformed

    private void txtPlanNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlanNameActionPerformed

    private void txtPlanPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlanPriceActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String planId = txtPlanId.getText();
        String planName = txtPlanName.getText();
        String planDuration = listPlanDuration.getSelectedValue();
        String planPrice = txtPlanPrice.getText();
        String planDescription = txtPlanDescription.getText();
        
        if(!(planId.isEmpty() || planName.isEmpty() || planPrice.isEmpty() || planDescription.isEmpty())){
            if(plan.validatePlanName(planName)){
                if(plan.validatePlanPrice(planPrice)){
                    if(!(planDuration == null || planDuration.equals("Select"))){
                        int planId1 = Integer.parseInt(planId);
                        double planPrice1 = Double.parseDouble(planPrice);
                        boolean accessToAllMachines = chkAccessToAllMachines.isSelected();
                        boolean personalTrainer = chkPersonalTrainer.isSelected();
                        boolean groupClasses = chkGroupClasses.isSelected();
                        boolean lockerAccess = chkLockerAccess.isSelected();
                        
                        plan = new MemberPlan(planId1, planName, planDuration, planPrice1, planDescription,
                                accessToAllMachines, personalTrainer, groupClasses, lockerAccess);
                        gym.addPlan(plan);
                        JOptionPane.showMessageDialog(null, "Plan information added successfully");
                        
                        // Clear fields
                        txtPlanId.setText("");
                        txtPlanName.setText("");
                        txtPlanPrice.setText("");
                        txtPlanDescription.setText("");
                        listPlanDuration.setSelectedIndex(0);
                        chkAccessToAllMachines.setSelected(false);
                        chkPersonalTrainer.setSelected(false);
                        chkGroupClasses.setSelected(false);
                        chkLockerAccess.setSelected(false);
                    } else{
                        JOptionPane.showMessageDialog(null, "Please select a plan duration.");
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Plan price must be a valid positive number.");
                    txtPlanPrice.setText("");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Plan name is invalid. Please enter a valid name (max 50 characters).");
                txtPlanName.setText("");
            }
        } else{
            JOptionPane.showMessageDialog(this, "Please fill in all fields before saving.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        dispose();
        JFramePlanMenu planMenu = new JFramePlanMenu();
        planMenu.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameAddPlan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnGeneratePlanId;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chkAccessToAllMachines;
    private javax.swing.JCheckBox chkGroupClasses;
    private javax.swing.JCheckBox chkLockerAccess;
    private javax.swing.JCheckBox chkPersonalTrainer;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listPlanDuration;
    private javax.swing.JTextArea txtPlanDescription;
    private javax.swing.JTextField txtPlanId;
    private javax.swing.JTextField txtPlanName;
    private javax.swing.JTextField txtPlanPrice;
    // End of variables declaration//GEN-END:variables
}
