package com.gymmanagement.reportanalytics.service;

import com.gymmanagement.reportanalytics.base.IReportService;
import com.gymmanagement.reportanalytics.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ReportService Implementation
 * 
 * This is the ReportManager/ReportService implementation for the Report Analytics Component.
 * It implements IReportService interface and provides all report generation and analytics functionality.
 */
@Service
public class ReportService implements IReportService {
    
    private final DataService dataService;
    
    public ReportService(DataService dataService) {
        this.dataService = dataService;
    }
    
    // ========== System Report Generation Methods ==========
    
    @Override
    public String generateFullReport() {
        LocalDate currentDate = LocalDate.now();
        String lines = "--------------------------------";
        String reportDetails = "\n Date : " + currentDate + "\n";
        reportDetails += generateTrainerReport() + lines 
                + generateMemberReport() + lines
                + generateMachineReport() + lines 
                + generateBookingReport() + lines 
                + generateReportOfOutstandingBalance() + lines
                + generateReportOfZeroOutstandingBalance() + lines;
        return reportDetails;
    }
    
    /**
     * Generate HTML formatted full report
     */
    public String generateFullReportHtml() {
        LocalDate currentDate = LocalDate.now();
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"en\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>Gym Management System - Full Report</title>\n");
        html.append("    <style>\n");
        html.append("        * { margin: 0; padding: 0; box-sizing: border-box; }\n");
        html.append("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 20px; min-height: 100vh; }\n");
        html.append("        .container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 12px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); overflow: hidden; }\n");
        html.append("        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 30px; text-align: center; }\n");
        html.append("        .header h1 { font-size: 2.5em; margin-bottom: 10px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); }\n");
        html.append("        .header .date { font-size: 1.2em; opacity: 0.9; }\n");
        html.append("        .nav-bar { margin-top: 20px; display: flex; flex-wrap: wrap; justify-content: center; gap: 10px; }\n");
        html.append("        .nav-btn { border: none; border-radius: 999px; padding: 8px 16px; font-size: 0.9em; cursor: pointer; background: rgba(255,255,255,0.15); color: #fff; backdrop-filter: blur(4px); display: inline-flex; align-items: center; gap: 6px; text-decoration: none; }\n");
        html.append("        .nav-btn:hover { background: rgba(255,255,255,0.3); }\n");
        html.append("        .nav-btn span.icon { font-size: 1.1em; }\n");
        html.append("        .content { padding: 30px; }\n");
        html.append("        .section { margin-bottom: 40px; border-left: 4px solid #667eea; padding-left: 20px; }\n");
        html.append("        .section-title { font-size: 1.8em; color: #667eea; margin-bottom: 20px; font-weight: 600; display: flex; align-items: center; }\n");
        html.append("        .section-title::before { content: '📊'; margin-right: 10px; font-size: 1.2em; }\n");
        html.append("        .item-list { list-style: none; }\n");
        html.append("        .item { background: #f8f9fa; padding: 12px 15px; margin-bottom: 8px; border-radius: 6px; border-left: 3px solid #667eea; transition: all 0.3s ease; }\n");
        html.append("        .item:hover { background: #e9ecef; transform: translateX(5px); box-shadow: 0 2px 8px rgba(102, 126, 234, 0.2); }\n");
        html.append("        .item-name { font-weight: 600; color: #333; }\n");
        html.append("        .item-id { color: #667eea; font-size: 0.9em; margin-left: 10px; }\n");
        html.append("        .item-amount { color: #28a745; font-weight: 600; float: right; }\n");
        html.append("        .empty-state { color: #6c757d; font-style: italic; padding: 20px; text-align: center; background: #f8f9fa; border-radius: 6px; }\n");
        html.append("        .divider { height: 2px; background: linear-gradient(to right, transparent, #667eea, transparent); margin: 30px 0; }\n");
        html.append("        @media (max-width: 768px) { .header h1 { font-size: 1.8em; } .content { padding: 20px; } }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <div class=\"header\">\n");
        html.append("            <h1>🏋️ Gym Management System</h1>\n");
        html.append("            <div class=\"date\">Report Generated: ").append(currentDate).append("</div>\n");
        html.append("            <div class=\"nav-bar\">\n");
        html.append("                <a class=\"nav-btn\" href=\"/api/reports/full\" target=\"_blank\"><span class=\"icon\">📄</span><span>View Full Report (Text)</span></a>\n");
        html.append("                <a class=\"nav-btn\" href=\"/api/reports/export/full/txt\" target=\"_blank\"><span class=\"icon\">⬇️</span><span>Download Full Report (TXT)</span></a>\n");
        html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/demographics/ui\" target=\"_blank\"><span class=\"icon\">👥</span><span>Member Demographics</span></a>\n");
        html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/growth-trends/ui\" target=\"_blank\"><span class=\"icon\">📈</span><span>Growth Trends</span></a>\n");
        html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/body-statistics/ui\" target=\"_blank\"><span class=\"icon\">⚖️</span><span>Body Statistics</span></a>\n");
        html.append("                <a class=\"nav-btn\" href=\"/api/analytics/member/fitness-goals/ui\" target=\"_blank\"><span class=\"icon\">🎯</span><span>Fitness Goals</span></a>\n");
        html.append("                <a class=\"nav-btn\" href=\"/api/analytics/equipment/usage/ui\" target=\"_blank\"><span class=\"icon\">🛠️</span><span>Equipment Usage</span></a>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        html.append("        <div class=\"content\">\n");
        
        // Trainers Section
        html.append("            <div class=\"section\">\n");
        html.append("                <div class=\"section-title\">Trainers List</div>\n");
        html.append("                <ul class=\"item-list\">\n");
        List<TrainerDTO> trainers = dataService.getAllTrainers();
        if (trainers.isEmpty()) {
            html.append("                    <li class=\"empty-state\">No trainers found</li>\n");
        } else {
            for (TrainerDTO t : trainers) {
                html.append("                    <li class=\"item\"><span class=\"item-name\">").append(escapeHtml(t.getName()))
                    .append("</span><span class=\"item-id\">ID: ").append(t.getRegId()).append("</span></li>\n");
            }
        }
        html.append("                </ul>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"divider\"></div>\n");
        
        // Members Section
        html.append("            <div class=\"section\">\n");
        html.append("                <div class=\"section-title\">Members List</div>\n");
        html.append("                <ul class=\"item-list\">\n");
        List<MemberDTO> members = dataService.getAllMembers();
        if (members.isEmpty()) {
            html.append("                    <li class=\"empty-state\">No members found</li>\n");
        } else {
            for (MemberDTO m : members) {
                html.append("                    <li class=\"item\"><span class=\"item-name\">").append(escapeHtml(m.getName()))
                    .append("</span><span class=\"item-id\">ID: ").append(m.getRegId()).append("</span></li>\n");
            }
        }
        html.append("                </ul>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"divider\"></div>\n");
        
        // Machines Section
        html.append("            <div class=\"section\">\n");
        html.append("                <div class=\"section-title\">Machines List</div>\n");
        html.append("                <ul class=\"item-list\">\n");
        List<MachineDTO> machines = dataService.getAllMachines();
        if (machines.isEmpty()) {
            html.append("                    <li class=\"empty-state\">No machines found</li>\n");
        } else {
            for (MachineDTO m : machines) {
                html.append("                    <li class=\"item\"><span class=\"item-name\">").append(escapeHtml(m.getName()))
                    .append("</span><span class=\"item-id\">ID: ").append(m.getRegId()).append("</span></li>\n");
            }
        }
        html.append("                </ul>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"divider\"></div>\n");
        
        // Bookings Section
        html.append("            <div class=\"section\">\n");
        html.append("                <div class=\"section-title\">Bookings List</div>\n");
        html.append("                <ul class=\"item-list\">\n");
        List<BookingDTO> bookings = dataService.getAllBookings();
        if (bookings.isEmpty()) {
            html.append("                    <li class=\"empty-state\">No bookings found</li>\n");
        } else {
            for (BookingDTO b : bookings) {
                html.append("                    <li class=\"item\"><span class=\"item-name\">")
                    .append(escapeHtml(b.getMachineName())).append(" - ").append(escapeHtml(b.getMemberName()))
                    .append("</span><span class=\"item-id\">Member ID: ").append(b.getMemberRegId()).append("</span></li>\n");
            }
        }
        html.append("                </ul>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"divider\"></div>\n");
        
        // Outstanding Balance Section
        html.append("            <div class=\"section\">\n");
        html.append("                <div class=\"section-title\">Members with Outstanding Balance</div>\n");
        html.append("                <ul class=\"item-list\">\n");
        boolean hasOutstanding = false;
        for (MemberDTO m : members) {
            if (m.getPayment() != null && "UnPaid".equals(m.getPayment().getStatus())) {
                hasOutstanding = true;
                html.append("                    <li class=\"item\"><span class=\"item-name\">").append(escapeHtml(m.getName()))
                    .append("</span><span class=\"item-id\">ID: ").append(m.getRegId())
                    .append("</span><span class=\"item-amount\">$").append(String.format("%.2f", m.getPayment().getOutstandingBalance())).append("</span></li>\n");
            }
        }
        if (!hasOutstanding) {
            html.append("                    <li class=\"empty-state\">No outstanding balances</li>\n");
        }
        html.append("                </ul>\n");
        html.append("            </div>\n");
        html.append("            <div class=\"divider\"></div>\n");
        
        // Zero Outstanding Balance Section
        html.append("            <div class=\"section\">\n");
        html.append("                <div class=\"section-title\">Members with Zero Outstanding Balance</div>\n");
        html.append("                <ul class=\"item-list\">\n");
        boolean hasPaid = false;
        for (MemberDTO m : members) {
            if (m.getPayment() != null && "Paid".equals(m.getPayment().getStatus())) {
                hasPaid = true;
                html.append("                    <li class=\"item\"><span class=\"item-name\">").append(escapeHtml(m.getName()))
                    .append("</span><span class=\"item-id\">ID: ").append(m.getRegId())
                    .append("</span><span class=\"item-amount\">$").append(String.format("%.2f", m.getPayment().getOutstandingBalance())).append("</span></li>\n");
            }
        }
        if (!hasPaid) {
            html.append("                    <li class=\"empty-state\">No members with zero balance</li>\n");
        }
        html.append("                </ul>\n");
        html.append("            </div>\n");
        
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        return html.toString();
    }
    
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#39;");
    }
    
    @Override
    public String generateTrainerReport() {
        String reportDetails = "";
        reportDetails += "\n >>> Trainers list\n\n";
        List<TrainerDTO> trainers = dataService.getAllTrainers();
        for (TrainerDTO t : trainers) {
            reportDetails += " " + t.getName() + " (" + t.getRegId() + ")\n";
        }
        return reportDetails;
    }
    
    @Override
    public String generateMemberReport() {
        String reportDetails = "";
        reportDetails += "\n >>> Members list\n\n";
        List<MemberDTO> members = dataService.getAllMembers();
        for (MemberDTO m : members) {
            reportDetails += " " + m.getName() + " (" + m.getRegId() + ")\n";
        }
        return reportDetails;
    }
    
    @Override
    public String generateMachineReport() {
        String reportDetails = "";
        reportDetails += "\n >>> Machines list\n\n";
        List<MachineDTO> machines = dataService.getAllMachines();
        for (MachineDTO m : machines) {
            reportDetails += " " + m.getName() + " (" + m.getRegId() + ")\n";
        }
        return reportDetails;
    }
    
    @Override
    public String generateBookingReport() {
        String reportDetails = "";
        reportDetails += "\n >>> Bookings list\n\n";
        List<BookingDTO> bookings = dataService.getAllBookings();
        for (BookingDTO b : bookings) {
            reportDetails += " " + b.getMachineName() + " - " + b.getMemberName() 
                    + " (" + b.getMemberRegId() + ")\n";
        }
        return reportDetails;
    }
    
    @Override
    public String generateReportOfOutstandingBalance() {
        String reportDetails = "";
        reportDetails += "\n >>> Members with Outstanding Balance\n\n";
        List<MemberDTO> members = dataService.getAllMembers();
        for (MemberDTO m : members) {
            if (m.getPayment() != null && "UnPaid".equals(m.getPayment().getStatus())) {
                reportDetails += " " + m.getName() + " (" + m.getRegId() + ") $" 
                        + m.getPayment().getOutstandingBalance() + "\n";
            }
        }
        return reportDetails;
    }
    
    @Override
    public String generateReportOfZeroOutstandingBalance() {
        String reportDetails = "";
        reportDetails += "\n >>> Members with Zero Outstanding Balance\n\n";
        List<MemberDTO> members = dataService.getAllMembers();
        for (MemberDTO m : members) {
            if (m.getPayment() != null && "Paid".equals(m.getPayment().getStatus())) {
                reportDetails += " " + m.getName() + " (" + m.getRegId() + ") $" 
                        + m.getPayment().getOutstandingBalance() + "\n";
            }
        }
        return reportDetails;
    }
    
    // ========== Member Analytics Methods (NEW FEATURE) ==========
    
    @Override
    public MemberDemographicsDTO generateMemberDemographicsReport() {
        LocalDate currentDate = LocalDate.now();
        List<MemberDTO> members = dataService.getAllMembers();
        
        if (members.isEmpty()) {
            return new MemberDemographicsDTO(0, new HashMap<>(), new HashMap<>(), 
                    new HashMap<>(), new HashMap<>(), currentDate.toString());
        }
        
        int totalMembers = members.size();
        
        // Gender distribution
        Map<String, Integer> genderDistribution = new HashMap<>();
        Map<String, Double> genderPercentages = new HashMap<>();
        
        for (MemberDTO m : members) {
            String gender = (m.getGender() != null) ? m.getGender().toLowerCase() : "not_specified";
            if (gender.equals("male") || gender.equals("m")) {
                gender = "male";
            } else if (gender.equals("female") || gender.equals("f")) {
                gender = "female";
            } else if (!gender.equals("not_specified")) {
                gender = "other";
            }
            genderDistribution.put(gender, genderDistribution.getOrDefault(gender, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : genderDistribution.entrySet()) {
            genderPercentages.put(entry.getKey(), (entry.getValue() * 100.0) / totalMembers);
        }
        
        // Age group distribution
        Map<String, Integer> ageGroupDistribution = new HashMap<>();
        Map<String, Double> ageGroupPercentages = new HashMap<>();
        
        for (MemberDTO m : members) {
            int age = m.getAge();
            String ageGroup;
            if (age < 18) {
                ageGroup = "under_18";
            } else if (age <= 25) {
                ageGroup = "18_25";
            } else if (age <= 35) {
                ageGroup = "26_35";
            } else if (age <= 45) {
                ageGroup = "36_45";
            } else if (age <= 55) {
                ageGroup = "46_55";
            } else {
                ageGroup = "56_plus";
            }
            ageGroupDistribution.put(ageGroup, ageGroupDistribution.getOrDefault(ageGroup, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : ageGroupDistribution.entrySet()) {
            ageGroupPercentages.put(entry.getKey(), (entry.getValue() * 100.0) / totalMembers);
        }
        
        return new MemberDemographicsDTO(totalMembers, genderDistribution, ageGroupDistribution,
                genderPercentages, ageGroupPercentages, currentDate.toString());
    }
    
    @Override
    public GrowthTrendsDTO generateGrowthTrendsReport() {
        LocalDate currentDate = LocalDate.now();
        List<MemberDTO> members = dataService.getAllMembers();
        
        Map<String, Integer> monthlyRegistrations = new HashMap<>();
        Map<Integer, Integer> yearlyRegistrations = new HashMap<>();
        
        for (MemberDTO m : members) {
            if (m.getJoinDate() != null) {
                int month = m.getJoinDate().getMonthValue();
                int year = m.getJoinDate().getYear();
                String monthYear = month + "/" + year;
                
                monthlyRegistrations.put(monthYear, monthlyRegistrations.getOrDefault(monthYear, 0) + 1);
                yearlyRegistrations.put(year, yearlyRegistrations.getOrDefault(year, 0) + 1);
            }
        }
        
        // Calculate growth rate
        Double growthRate = null;
        String growthRatePeriod = null;
        List<Integer> years = new ArrayList<>(yearlyRegistrations.keySet());
        Collections.sort(years);
        
        if (years.size() >= 2) {
            int currentYear = years.get(years.size() - 1);
            int previousYear = years.get(years.size() - 2);
            int currentCount = yearlyRegistrations.get(currentYear);
            int previousCount = yearlyRegistrations.get(previousYear);
            
            if (previousCount > 0) {
                growthRate = ((currentCount - previousCount) * 100.0) / previousCount;
                growthRatePeriod = previousYear + " to " + currentYear;
            }
        }
        
        return new GrowthTrendsDTO(monthlyRegistrations, yearlyRegistrations, 
                growthRate, growthRatePeriod, currentDate.toString());
    }
    
    @Override
    public BodyStatisticsDTO generateBodyStatisticsReport() {
        LocalDate currentDate = LocalDate.now();
        List<MemberDTO> members = dataService.getAllMembers();
        
        double totalHeight = 0;
        double totalWeight = 0;
        double totalBMI = 0;
        int validRecords = 0;
        
        Map<String, Integer> bmiCategoryDistribution = new HashMap<>();
        Map<String, Double> bmiCategoryPercentages = new HashMap<>();
        
        for (MemberDTO m : members) {
            if (m.getHeight() > 0 && m.getWeight() > 0) {
                totalHeight += m.getHeight();
                totalWeight += m.getWeight();
                double bmi = m.getBmi();
                totalBMI += bmi;
                validRecords++;
                
                String category;
                if (bmi < 18.5) {
                    category = "underweight";
                } else if (bmi < 25) {
                    category = "normal";
                } else if (bmi < 30) {
                    category = "overweight";
                } else {
                    category = "obese";
                }
                bmiCategoryDistribution.put(category, 
                        bmiCategoryDistribution.getOrDefault(category, 0) + 1);
            }
        }
        
        if (validRecords > 0) {
            for (Map.Entry<String, Integer> entry : bmiCategoryDistribution.entrySet()) {
                bmiCategoryPercentages.put(entry.getKey(), 
                        (entry.getValue() * 100.0) / validRecords);
            }
        }
        
        double avgHeight = validRecords > 0 ? totalHeight / validRecords : 0;
        double avgWeight = validRecords > 0 ? totalWeight / validRecords : 0;
        double avgBMI = validRecords > 0 ? totalBMI / validRecords : 0;
        
        return new BodyStatisticsDTO(avgHeight, avgWeight, avgBMI, 
                bmiCategoryDistribution, bmiCategoryPercentages, validRecords, currentDate.toString());
    }
    
    @Override
    public FitnessGoalDistributionDTO generateFitnessGoalDistributionReport() {
        LocalDate currentDate = LocalDate.now();
        List<MemberDTO> members = dataService.getAllMembers();
        
        int totalMembers = members.size();
        Map<String, Integer> goalDistribution = new HashMap<>();
        Map<String, Double> goalPercentages = new HashMap<>();
        int membersWithGoals = 0;
        
        for (MemberDTO m : members) {
            if (m.getFitnessGoal() != null && !m.getFitnessGoal().trim().isEmpty()) {
                String goal = m.getFitnessGoal().trim();
                goalDistribution.put(goal, goalDistribution.getOrDefault(goal, 0) + 1);
                membersWithGoals++;
            }
        }
        
        if (membersWithGoals > 0) {
            for (Map.Entry<String, Integer> entry : goalDistribution.entrySet()) {
                goalPercentages.put(entry.getKey(), 
                        (entry.getValue() * 100.0) / membersWithGoals);
            }
        }
        
        return new FitnessGoalDistributionDTO(totalMembers, membersWithGoals, 
                goalDistribution, goalPercentages, currentDate.toString());
    }
    
    // ========== Equipment Usage Analytics Method (NEW FEATURE) ==========
    
    @Override
    public EquipmentUsageAnalyticsDTO generateEquipmentUsageAnalyticsReport() {
        LocalDate currentDate = LocalDate.now();
        List<MachineDTO> machines = dataService.getAllMachines();
        
        if (machines.isEmpty()) {
            return new EquipmentUsageAnalyticsDTO(0, 0, 0, 0.0, 0, 0, 0, 
                    new HashMap<>(), new HashMap<>(), new HashMap<>(), currentDate.toString());
        }
        
        int totalMachines = machines.size();
        int totalCapacity = totalMachines * 8; // Each machine has capacity of 8 bookings
        int totalBookings = 0;
        int machinesWithBookings = 0;
        int machinesFullyBooked = 0;
        int machinesUnused = 0;
        
        Map<String, Integer> machineBookingCount = new HashMap<>();
        Map<String, Double> machineUtilization = new HashMap<>();
        
        for (MachineDTO m : machines) {
            int machineBookings = (m.getBookings() != null) ? m.getBookings().size() : 0;
            totalBookings += machineBookings;
            
            String machineKey = m.getName() + " (" + m.getRegId() + ")";
            machineBookingCount.put(machineKey, machineBookings);
            
            double utilization = (machineBookings * 100.0) / 8.0;
            machineUtilization.put(machineKey, utilization);
            
            if (machineBookings > 0) {
                machinesWithBookings++;
            }
            if (machineBookings == 8) {
                machinesFullyBooked++;
            }
            if (machineBookings == 0) {
                machinesUnused++;
            }
        }
        
        double averageUtilization = (totalBookings * 100.0) / totalCapacity;
        
        Map<String, Double> capacityDistribution = new HashMap<>();
        capacityDistribution.put("with_bookings", (machinesWithBookings * 100.0) / totalMachines);
        capacityDistribution.put("fully_booked", (machinesFullyBooked * 100.0) / totalMachines);
        capacityDistribution.put("unused", (machinesUnused * 100.0) / totalMachines);
        
        return new EquipmentUsageAnalyticsDTO(totalMachines, totalCapacity, totalBookings,
                averageUtilization, machinesWithBookings, machinesFullyBooked, machinesUnused,
                machineBookingCount, machineUtilization, capacityDistribution, currentDate.toString());
    }
    
    // ========== Export Methods (NEW FEATURE) ==========
    
    @Override
    public byte[] exportReportToTxt(String reportContent, String reportType) {
        return reportContent.getBytes();
    }
    
    @Override
    public byte[] exportReportToJson(Object reportData, String reportType) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = 
                    new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.writeValueAsBytes(reportData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export to JSON", e);
        }
    }
    
    @Override
    public byte[] exportReportToCsv(String reportContent, String reportType) {
        // Simple CSV conversion - can be enhanced
        StringBuilder csv = new StringBuilder();
        String[] lines = reportContent.split("\n");
        for (String line : lines) {
            csv.append(line.replace(",", ";")).append("\n");
        }
        return csv.toString().getBytes();
    }
}

