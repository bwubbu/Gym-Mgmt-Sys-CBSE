package project_oop;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.time.LocalDate;
public class JFrameMemberAnalytics extends javax.swing.JFrame {
    Report report = new Report();
    private String generatedReport;
    private String currentReportType = "";
    public JFrameMemberAnalytics() {
        initComponents();
        txtAnalyticsReport.setEditable(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        iLabel1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMenuOption = new javax.swing.JTextField();
        btnAnalyze = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAnalyticsReport = new javax.swing.JTextArea();
        btnBack = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(35, 43, 42));

        iLabel1.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        iLabel1.setForeground(new java.awt.Color(255, 255, 255));
        iLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iLabel1.setText("Member Analytics");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("1. View member demographics");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("2. Analyze growth trends");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("3. View body statistics analysis");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("4. View fitness goal distribution");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Choose Option:");

        txtMenuOption.setBackground(new java.awt.Color(35, 43, 42));
        txtMenuOption.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        txtMenuOption.setForeground(new java.awt.Color(255, 255, 255));
        txtMenuOption.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMenuOption.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        txtMenuOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMenuOptionActionPerformed(evt);
            }
        });

        btnAnalyze.setBackground(new java.awt.Color(17, 122, 102));
        btnAnalyze.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAnalyze.setForeground(new java.awt.Color(255, 255, 255));
        btnAnalyze.setText("Analyze");
        btnAnalyze.setBorder(null);
        btnAnalyze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalyzeActionPerformed(evt);
            }
        });

        txtAnalyticsReport.setBackground(new java.awt.Color(35, 43, 42));
        txtAnalyticsReport.setColumns(20);
        txtAnalyticsReport.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAnalyticsReport.setForeground(new java.awt.Color(255, 255, 255));
        txtAnalyticsReport.setRows(5);
        jScrollPane1.setViewportView(txtAnalyticsReport);

        btnBack.setBackground(new java.awt.Color(17, 122, 102));
        btnBack.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("GO BACK");
        btnBack.setBorder(null);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(17, 122, 102));
        btnReset.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("RESET");
        btnReset.setBorder(null);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnExport.setBackground(new java.awt.Color(17, 122, 102));
        btnExport.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("EXPORT");
        btnExport.setBorder(null);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtMenuOption, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(200, 200, 200))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(268, 268, 268)
                .addComponent(btnAnalyze, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(iLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMenuOption, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAnalyze, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMenuOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMenuOptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMenuOptionActionPerformed

    private void btnAnalyzeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalyzeActionPerformed
        String option = txtMenuOption.getText();
        if (option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4")){
            switch(option){
                case "1":
                    generatedReport = report.generateMemberDemographicsReport();
                    currentReportType = "MemberDemographics";
                    txtAnalyticsReport.setText(generatedReport);
                    txtAnalyticsReport.setEditable(false);
                    break;
                case "2":
                    generatedReport = report.generateGrowthTrendsReport();
                    currentReportType = "GrowthTrends";
                    txtAnalyticsReport.setText(generatedReport);
                    txtAnalyticsReport.setEditable(false);
                    break;
                case "3":
                    generatedReport = report.generateBodyStatisticsReport();
                    currentReportType = "BodyStatistics";
                    txtAnalyticsReport.setText(generatedReport);
                    txtAnalyticsReport.setEditable(false);
                    break;
                case "4":
                    generatedReport = report.generateFitnessGoalDistributionReport();
                    currentReportType = "FitnessGoalDistribution";
                    txtAnalyticsReport.setText(generatedReport);
                    txtAnalyticsReport.setEditable(false);
                    break;
            }
        } else{
            JOptionPane.showMessageDialog(null, "You enter wrong choice, enter again.");
        }
    }//GEN-LAST:event_btnAnalyzeActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        dispose();
        JFrameAnalyticsMenu analyticsMenu = new JFrameAnalyticsMenu();
        analyticsMenu.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtAnalyticsReport.setText("");
        currentReportType = "";
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        String reportContent = txtAnalyticsReport.getText().trim();
        
        // Check if report is empty
        if(reportContent.isEmpty()){
            JOptionPane.showMessageDialog(null, "No report to export. Please generate a report first.");
            return;
        }
        
        // Generate default filename
        LocalDate currentDate = LocalDate.now();
        String defaultFilename = "Report_" + currentReportType + "_" + currentDate + ".txt";
        
        // Create file chooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Report");
        fileChooser.setSelectedFile(new File(defaultFilename));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files (*.txt)", "txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        
        if(userSelection == JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            String fileName = fileToSave.getName();
            
            // Validate filename
            if(fileName.contains("/") || fileName.contains("\\") || fileName.contains(":") || 
               fileName.contains("*") || fileName.contains("?") || fileName.contains("\"") || 
               fileName.contains("<") || fileName.contains(">") || fileName.contains("|")){
                JOptionPane.showMessageDialog(null, "Invalid filename. Please use valid characters only.");
                return;
            }
            
            // Ensure .txt extension
            if(!fileName.toLowerCase().endsWith(".txt")){
                fileToSave = new File(fileToSave.getParent(), fileName + ".txt");
            }
            
            // Write file
            try {
                FileWriter writer = new FileWriter(fileToSave);
                writer.write(reportContent);
                writer.close();
                JOptionPane.showMessageDialog(null, "Report exported successfully to:\n" + fileToSave.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to export report. Please check file permissions.\nError: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnExportActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameMemberAnalytics().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalyze;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnReset;
    private javax.swing.JLabel iLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtMenuOption;
    private javax.swing.JTextArea txtAnalyticsReport;
    // End of variables declaration//GEN-END:variables
}

