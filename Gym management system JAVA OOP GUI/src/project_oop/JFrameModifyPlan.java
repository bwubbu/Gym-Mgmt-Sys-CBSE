package project_oop;
import javax.swing.*;
public class JFrameModifyPlan extends javax.swing.JFrame {
    Gym gym = new Gym();
    MemberPlan plan = new MemberPlan();
    int currentPlanId = 0;
    
    public JFrameModifyPlan() {
        initComponents();
        txtNewPlanName.setEditable(false);
        txtNewPlanPrice.setEditable(false);
        txtNewPlanDescription.setEditable(false);
        listNewPlanDuration.setEnabled(false);
        chkNewAccessToAllMachines.setEnabled(false);
        chkNewPersonalTrainer.setEnabled(false);
        chkNewGroupClasses.setEnabled(false);
        chkNewLockerAccess.setEnabled(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPlanId = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnSearchPlan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNewPlanName = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listNewPlanDuration = new javax.swing.JList<>();
        jLabel16 = new javax.swing.JLabel();
        txtNewPlanPrice = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNewPlanDescription = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        chkNewAccessToAllMachines = new javax.swing.JCheckBox();
        chkNewPersonalTrainer = new javax.swing.JCheckBox();
        chkNewGroupClasses = new javax.swing.JCheckBox();
        chkNewLockerAccess = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(35, 43, 42));

        txtPlanId.setBackground(new java.awt.Color(35, 43, 42));
        txtPlanId.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtPlanId.setForeground(new java.awt.Color(255, 255, 255));
        txtPlanId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtPlanId.setMargin(new java.awt.Insets(2, 1, 2, 1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Plan ID:");

        btnSearchPlan.setBackground(new java.awt.Color(17, 122, 102));
        btnSearchPlan.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnSearchPlan.setForeground(new java.awt.Color(255, 255, 255));
        btnSearchPlan.setText("SEARCH PLAN");
        btnSearchPlan.setBorder(null);
        btnSearchPlan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchPlanActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Modify Plan Information");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Plan Name:");

        txtNewPlanName.setBackground(new java.awt.Color(35, 43, 42));
        txtNewPlanName.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtNewPlanName.setForeground(new java.awt.Color(255, 255, 255));
        txtNewPlanName.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtNewPlanName.setMargin(new java.awt.Insets(2, 1, 2, 1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Plan Duration:");

        listNewPlanDuration.setBackground(new java.awt.Color(35, 43, 42));
        listNewPlanDuration.setForeground(new java.awt.Color(255, 255, 255));
        listNewPlanDuration.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Select", "Monthly", "Quarterly", "Annual" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        listNewPlanDuration.setSelectedIndex(0);
        jScrollPane1.setViewportView(listNewPlanDuration);

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Plan Price:");

        txtNewPlanPrice.setBackground(new java.awt.Color(35, 43, 42));
        txtNewPlanPrice.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtNewPlanPrice.setForeground(new java.awt.Color(255, 255, 255));
        txtNewPlanPrice.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtNewPlanPrice.setMargin(new java.awt.Insets(2, 1, 2, 1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Plan Description:");

        txtNewPlanDescription.setBackground(new java.awt.Color(35, 43, 42));
        txtNewPlanDescription.setColumns(20);
        txtNewPlanDescription.setFont(new java.awt.Font("Tahoma", 0, 13));
        txtNewPlanDescription.setForeground(new java.awt.Color(255, 255, 255));
        txtNewPlanDescription.setRows(5);
        txtNewPlanDescription.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        jScrollPane2.setViewportView(txtNewPlanDescription);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Plan Features:");

        chkNewAccessToAllMachines.setForeground(new java.awt.Color(255, 255, 255));
        chkNewAccessToAllMachines.setText("Access to all machines");

        chkNewPersonalTrainer.setForeground(new java.awt.Color(255, 255, 255));
        chkNewPersonalTrainer.setText("Personal trainer included");

        chkNewGroupClasses.setForeground(new java.awt.Color(255, 255, 255));
        chkNewGroupClasses.setText("Group classes included");

        chkNewLockerAccess.setForeground(new java.awt.Color(255, 255, 255));
        chkNewLockerAccess.setText("Locker access");

        btnSave.setBackground(new java.awt.Color(17, 122, 102));
        btnSave.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("SAVE");
        btnSave.setBorder(null);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(17, 122, 102));
        btnBack.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.setBorder(null);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Modify Plan");

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
                                .addComponent(btnSearchPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNewPlanName, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNewPlanPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chkNewAccessToAllMachines)
                                    .addComponent(chkNewPersonalTrainer)
                                    .addComponent(chkNewGroupClasses)
                                    .addComponent(chkNewLockerAccess))))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlanId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchPlan))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewPlanName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNewPlanPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chkNewAccessToAllMachines)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkNewPersonalTrainer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkNewGroupClasses)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkNewLockerAccess)))
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

    private void btnSearchPlanActionPerformed(javax.swing.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchPlanActionPerformed
        String planId = txtPlanId.getText();
        if(!planId.isEmpty()){
            if(gym.checkNumberIsInt(planId)){
                int id = Integer.parseInt(planId);
                if(gym.planExist(id)){
                    txtNewPlanName.setEditable(true);
                    txtNewPlanPrice.setEditable(true);
                    txtNewPlanDescription.setEditable(true);
                    listNewPlanDuration.setEnabled(true);
                    chkNewAccessToAllMachines.setEnabled(true);
                    chkNewPersonalTrainer.setEnabled(true);
                    chkNewGroupClasses.setEnabled(true);
                    chkNewLockerAccess.setEnabled(true);
                    
                    plan = gym.planData(id);
                    currentPlanId = id;
                    
                    // Populate fields with existing data
                    txtNewPlanName.setText(plan.getPlanName());
                    txtNewPlanPrice.setText(String.valueOf(plan.getPlanPrice()));
                    txtNewPlanDescription.setText(plan.getPlanDescription());
                    
                    // Set duration
                    String duration = plan.getPlanDuration();
                    if(duration != null){
                        if(duration.equals("Monthly")) listNewPlanDuration.setSelectedIndex(1);
                        else if(duration.equals("Quarterly")) listNewPlanDuration.setSelectedIndex(2);
                        else if(duration.equals("Annual")) listNewPlanDuration.setSelectedIndex(3);
                    }
                    
                    // Set checkboxes
                    chkNewAccessToAllMachines.setSelected(plan.getAccessToAllMachines());
                    chkNewPersonalTrainer.setSelected(plan.getPersonalTrainerIncluded());
                    chkNewGroupClasses.setSelected(plan.getGroupClassesIncluded());
                    chkNewLockerAccess.setSelected(plan.getLockerAccess());
                    
                    JOptionPane.showMessageDialog(null, "Plan found. Fields are now editable.");
                } else{
                    JOptionPane.showMessageDialog(null, "No plan found with ID " + planId + ". Please enter correct plan ID.");
                    txtPlanId.setText("");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Plan ID should be a number. Please enter again.");
                txtPlanId.setText("");
            }
        } else{
            JOptionPane.showMessageDialog(null, "Please enter the Plan ID first.");
        }
    }//GEN-LAST:event_btnSearchPlanActionPerformed

    private void btnSaveActionPerformed(javax.swing.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if(currentPlanId == 0){
            JOptionPane.showMessageDialog(null, "Please search for a plan first.");
            return;
        }
        
        String planName = txtNewPlanName.getText();
        String planPrice = txtNewPlanPrice.getText();
        String planDescription = txtNewPlanDescription.getText();
        String planDuration = listNewPlanDuration.getSelectedValue();
        
        if(!(planName.isEmpty() || planPrice.isEmpty() || planDescription.isEmpty())){
            if(plan.validatePlanName(planName)){
                if(plan.validatePlanPrice(planPrice)){
                    if(!(planDuration == null || planDuration.equals("Select"))){
                        double planPrice1 = Double.parseDouble(planPrice);
                        boolean accessToAllMachines = chkNewAccessToAllMachines.isSelected();
                        boolean personalTrainer = chkNewPersonalTrainer.isSelected();
                        boolean groupClasses = chkNewGroupClasses.isSelected();
                        boolean lockerAccess = chkNewLockerAccess.isSelected();
                        
                        plan = new MemberPlan(currentPlanId, planName, planDuration, planPrice1, planDescription,
                                accessToAllMachines, personalTrainer, groupClasses, lockerAccess);
                        gym.modifyPlan(plan, currentPlanId);
                        JOptionPane.showMessageDialog(null, "Plan information updated successfully.");
                        
                        // Clear and disable fields
                        txtPlanId.setText("");
                        txtNewPlanName.setText("");
                        txtNewPlanPrice.setText("");
                        txtNewPlanDescription.setText("");
                        listNewPlanDuration.setSelectedIndex(0);
                        chkNewAccessToAllMachines.setSelected(false);
                        chkNewPersonalTrainer.setSelected(false);
                        chkNewGroupClasses.setSelected(false);
                        chkNewLockerAccess.setSelected(false);
                        
                        txtNewPlanName.setEditable(false);
                        txtNewPlanPrice.setEditable(false);
                        txtNewPlanDescription.setEditable(false);
                        listNewPlanDuration.setEnabled(false);
                        chkNewAccessToAllMachines.setEnabled(false);
                        chkNewPersonalTrainer.setEnabled(false);
                        chkNewGroupClasses.setEnabled(false);
                        chkNewLockerAccess.setEnabled(false);
                        
                        currentPlanId = 0;
                    } else{
                        JOptionPane.showMessageDialog(null, "Please select a plan duration.");
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "Plan price must be a valid positive number.");
                    txtNewPlanPrice.setText("");
                }
            } else{
                JOptionPane.showMessageDialog(null, "Plan name is invalid. Please enter a valid name (max 50 characters).");
                txtNewPlanName.setText("");
            }
        } else{
            JOptionPane.showMessageDialog(this, "Please fill in all required fields before saving.");
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(javax.swing.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        dispose();
        JFramePlanMenu planMenu = new JFramePlanMenu();
        planMenu.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameModifyPlan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearchPlan;
    private javax.swing.JCheckBox chkNewAccessToAllMachines;
    private javax.swing.JCheckBox chkNewGroupClasses;
    private javax.swing.JCheckBox chkNewLockerAccess;
    private javax.swing.JCheckBox chkNewPersonalTrainer;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JList<String> listNewPlanDuration;
    private javax.swing.JTextArea txtNewPlanDescription;
    private javax.swing.JTextField txtNewPlanName;
    private javax.swing.JTextField txtNewPlanPrice;
    private javax.swing.JTextField txtPlanId;
    // End of variables declaration//GEN-END:variables
}
