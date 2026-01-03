package com.gymmanagement.osgi.report.internal;

import com.gymmanagement.osgi.base.entity.*;
import com.gymmanagement.osgi.base.service.*;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OSGi service implementation for Report and Analytics
 * 
 * Note: Service registration is handled via XML descriptor:
 * OSGI-INF/com.gymmanagement.osgi.report.service.xml
 */
public class ReportServiceImpl implements IReportService {
    
    private IMemberService memberService;
    private ITrainerService trainerService;
    private IMachineService machineService;
    private IPaymentService paymentService;
    
    /**
     * Get BundleContext from the framework
     */
    private BundleContext getBundleContext() {
        return FrameworkUtil.getBundle(this.getClass()).getBundleContext();
    }
    
    /**
     * Get member service from OSGi service registry
     */
    private IMemberService getMemberService() {
        if (memberService == null) {
            try {
                BundleContext context = getBundleContext();
                if (context != null) {
                    ServiceReference<IMemberService> ref = context.getServiceReference(IMemberService.class);
                    if (ref != null) {
                        memberService = context.getService(ref);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error getting IMemberService: " + e.getMessage());
            }
        }
        return memberService;
    }
    
    /**
     * Get trainer service from OSGi service registry
     */
    private ITrainerService getTrainerService() {
        if (trainerService == null) {
            try {
                BundleContext context = getBundleContext();
                if (context != null) {
                    ServiceReference<ITrainerService> ref = context.getServiceReference(ITrainerService.class);
                    if (ref != null) {
                        trainerService = context.getService(ref);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error getting ITrainerService: " + e.getMessage());
            }
        }
        return trainerService;
    }
    
    /**
     * Get machine service from OSGi service registry
     */
    private IMachineService getMachineService() {
        if (machineService == null) {
            try {
                BundleContext context = getBundleContext();
                if (context != null) {
                    ServiceReference<IMachineService> ref = context.getServiceReference(IMachineService.class);
                    if (ref != null) {
                        machineService = context.getService(ref);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error getting IMachineService: " + e.getMessage());
            }
        }
        return machineService;
    }
    
    /**
     * Get payment service from OSGi service registry
     */
    private IPaymentService getPaymentService() {
        if (paymentService == null) {
            try {
                BundleContext context = getBundleContext();
                if (context != null) {
                    ServiceReference<IPaymentService> ref = context.getServiceReference(IPaymentService.class);
                    if (ref != null) {
                        paymentService = context.getService(ref);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error getting IPaymentService: " + e.getMessage());
            }
        }
        return paymentService;
    }
    
    // ========== System Report Generation Methods ==========
    
    @Override
    public String generateFullReport() {
        LocalDate currentDate = LocalDate.now();
        String lines = "--------------------------------";
        StringBuilder report = new StringBuilder();
        report.append("\n Date : ").append(currentDate).append("\n");
        report.append(generateTrainerReport()).append(lines);
        report.append(generateMemberReport()).append(lines);
        report.append(generateMachineReport()).append(lines);
        report.append(generateBookingReport()).append(lines);
        report.append(generateReportOfOutstandingBalance()).append(lines);
        report.append(generateReportOfZeroOutstandingBalance()).append(lines);
        return report.toString();
    }
    
    @Override
    public String generateTrainerReport() {
        ITrainerService service = getTrainerService();
        if (service == null) {
            return "\nTrainer Report: Service not available\n";
        }
        
        List<Trainer> trainers = service.getAllTrainers();
        StringBuilder report = new StringBuilder("\n\t\tTrainer Report\n");
        report.append("---------------------------------------------------------------\n");
        
        if (trainers.isEmpty()) {
            report.append("No trainers found.\n");
        } else {
            for (Trainer trainer : trainers) {
                report.append(trainer.toString()).append("\n");
            }
        }
        report.append("---------------------------------------------------------------\n");
        return report.toString();
    }
    
    @Override
    public String generateMemberReport() {
        IMemberService service = getMemberService();
        if (service == null) {
            return "\nMember Report: Service not available\n";
        }
        
        List<Member> members = service.getAllMembers();
        StringBuilder report = new StringBuilder("\n\t\tMember Report\n");
        report.append("---------------------------------------------------------------\n");
        
        if (members.isEmpty()) {
            report.append("No members found.\n");
        } else {
            for (Member member : members) {
                report.append(member.toString()).append("\n");
            }
        }
        report.append("---------------------------------------------------------------\n");
        return report.toString();
    }
    
    @Override
    public String generateMachineReport() {
        IMachineService service = getMachineService();
        if (service == null) {
            return "\nMachine Report: Service not available\n";
        }
        
        List<Machine> machines = service.getAllMachines();
        StringBuilder report = new StringBuilder("\n\t\tMachine Report\n");
        report.append("---------------------------------------------------------------\n");
        
        if (machines.isEmpty()) {
            report.append("No machines found.\n");
        } else {
            for (Machine machine : machines) {
                report.append(machine.toString()).append("\n");
            }
        }
        report.append("---------------------------------------------------------------\n");
        return report.toString();
    }
    
    @Override
    public String generateBookingReport() {
        IMachineService service = getMachineService();
        if (service == null) {
            return "\nBooking Report: Service not available\n";
        }
        
        List<Machine> machines = service.getAllMachines();
        StringBuilder report = new StringBuilder("\n\t\tBooking Report\n");
        report.append("---------------------------------------------------------------\n");
        
        if (machines.isEmpty()) {
            report.append("No machines found.\n");
        } else {
            for (Machine machine : machines) {
                String bookings = service.getAllBookings(machine.getRegId());
                if (bookings != null && !bookings.trim().isEmpty()) {
                    report.append(bookings).append("\n");
                }
            }
        }
        report.append("---------------------------------------------------------------\n");
        return report.toString();
    }
    
    @Override
    public String generateReportOfOutstandingBalance() {
        IPaymentService service = getPaymentService();
        if (service == null) {
            return "\nOutstanding Balance Report: Service not available\n";
        }
        
        List<Member> members = service.getMembersWithOutstandingBalance();
        StringBuilder report = new StringBuilder("\n\t\tOutstanding Balance Report\n");
        report.append("---------------------------------------------------------------\n");
        
        if (members.isEmpty()) {
            report.append("No members with outstanding balance.\n");
        } else {
            for (Member member : members) {
                if (member.getPayment() != null) {
                    report.append("Member: ").append(member.getName())
                          .append(" (ID: ").append(member.getRegId()).append(")")
                          .append(" - Outstanding Balance: $")
                          .append(member.getPayment().getOutstandingBalance()).append("\n");
                }
            }
        }
        report.append("---------------------------------------------------------------\n");
        return report.toString();
    }
    
    @Override
    public String generateReportOfZeroOutstandingBalance() {
        IPaymentService service = getPaymentService();
        if (service == null) {
            return "\nZero Outstanding Balance Report: Service not available\n";
        }
        
        List<Member> members = service.getMembersWithZeroBalance();
        StringBuilder report = new StringBuilder("\n\t\tZero Outstanding Balance Report\n");
        report.append("---------------------------------------------------------------\n");
        
        if (members.isEmpty()) {
            report.append("No members with zero outstanding balance.\n");
        } else {
            for (Member member : members) {
                report.append("Member: ").append(member.getName())
                      .append(" (ID: ").append(member.getRegId()).append(")")
                      .append(" - Balance: $0.00\n");
            }
        }
        report.append("---------------------------------------------------------------\n");
        return report.toString();
    }
    
    // ========== Member Analytics Methods ==========
    
    @Override
    public Map<String, Object> generateMemberDemographicsReport() {
        IMemberService service = getMemberService();
        if (service == null) {
            return Collections.singletonMap("error", "Member service not available");
        }
        
        List<Member> members = service.getAllMembers();
        Map<String, Object> demographics = new HashMap<>();
        
        // Gender distribution
        Map<String, Long> genderDistribution = members.stream()
                .filter(m -> m.getGender() != null)
                .collect(Collectors.groupingBy(Member::getGender, Collectors.counting()));
        
        // Age group distribution
        Map<String, Long> ageGroups = new HashMap<>();
        ageGroups.put("18-25", members.stream().filter(m -> m.getAge() >= 18 && m.getAge() <= 25).count());
        ageGroups.put("26-35", members.stream().filter(m -> m.getAge() >= 26 && m.getAge() <= 35).count());
        ageGroups.put("36-45", members.stream().filter(m -> m.getAge() >= 36 && m.getAge() <= 45).count());
        ageGroups.put("46-55", members.stream().filter(m -> m.getAge() >= 46 && m.getAge() <= 55).count());
        ageGroups.put("56+", members.stream().filter(m -> m.getAge() >= 56).count());
        
        demographics.put("totalMembers", members.size());
        demographics.put("genderDistribution", genderDistribution);
        demographics.put("ageGroupDistribution", ageGroups);
        demographics.put("generatedDate", LocalDate.now().toString());
        
        return demographics;
    }
    
    @Override
    public Map<String, Object> generateGrowthTrendsReport() {
        IMemberService service = getMemberService();
        if (service == null) {
            return Collections.singletonMap("error", "Member service not available");
        }
        
        List<Member> members = service.getAllMembers();
        Map<String, Object> trends = new HashMap<>();
        
        // Monthly registrations (simplified - would need join date parsing)
        Map<String, Long> monthlyRegistrations = new HashMap<>();
        int currentMonth = LocalDate.now().getMonthValue();
        monthlyRegistrations.put(String.valueOf(currentMonth), (long) members.size());
        
        // Yearly registrations
        int currentYear = Year.now().getValue();
        long yearlyRegistrations = members.size();
        
        trends.put("monthlyRegistrations", monthlyRegistrations);
        trends.put("yearlyRegistrations", yearlyRegistrations);
        trends.put("growthRate", 0.0); // Would calculate based on historical data
        trends.put("generatedDate", LocalDate.now().toString());
        
        return trends;
    }
    
    @Override
    public Map<String, Object> generateBodyStatisticsReport() {
        IMemberService service = getMemberService();
        if (service == null) {
            return Collections.singletonMap("error", "Member service not available");
        }
        
        List<Member> members = service.getAllMembers();
        Map<String, Object> stats = new HashMap<>();
        
        List<Member> validMembers = members.stream()
                .filter(m -> m.getHeight() > 0 && m.getWeight() > 0)
                .collect(Collectors.toList());
        
        if (validMembers.isEmpty()) {
            stats.put("error", "No valid body statistics data available");
            return stats;
        }
        
        double avgHeight = validMembers.stream()
                .mapToDouble(Member::getHeight)
                .average()
                .orElse(0.0);
        
        double avgWeight = validMembers.stream()
                .mapToDouble(Member::getWeight)
                .average()
                .orElse(0.0);
        
        double avgBMI = validMembers.stream()
                .mapToDouble(Member::getBmi)
                .average()
                .orElse(0.0);
        
        // BMI category distribution
        Map<String, Long> bmiCategories = new HashMap<>();
        bmiCategories.put("Underweight", validMembers.stream().filter(m -> m.getBmi() < 18.5).count());
        bmiCategories.put("Normal", validMembers.stream().filter(m -> m.getBmi() >= 18.5 && m.getBmi() < 25).count());
        bmiCategories.put("Overweight", validMembers.stream().filter(m -> m.getBmi() >= 25 && m.getBmi() < 30).count());
        bmiCategories.put("Obese", validMembers.stream().filter(m -> m.getBmi() >= 30).count());
        
        stats.put("averageHeight", avgHeight);
        stats.put("averageWeight", avgWeight);
        stats.put("averageBMI", avgBMI);
        stats.put("bmiCategoryDistribution", bmiCategories);
        stats.put("validRecords", validMembers.size());
        stats.put("generatedDate", LocalDate.now().toString());
        
        return stats;
    }
    
    @Override
    public Map<String, Object> generateFitnessGoalDistributionReport() {
        IMemberService service = getMemberService();
        if (service == null) {
            return Collections.singletonMap("error", "Member service not available");
        }
        
        List<Member> members = service.getAllMembers();
        Map<String, Object> distribution = new HashMap<>();
        
        Map<String, Long> goalDistribution = members.stream()
                .filter(m -> m.getFitnessGoal() != null && !m.getFitnessGoal().trim().isEmpty())
                .collect(Collectors.groupingBy(Member::getFitnessGoal, Collectors.counting()));
        
        distribution.put("totalMembers", members.size());
        distribution.put("membersWithGoals", goalDistribution.values().stream().mapToLong(Long::longValue).sum());
        distribution.put("goalDistribution", goalDistribution);
        distribution.put("generatedDate", LocalDate.now().toString());
        
        return distribution;
    }
    
    // ========== Equipment Usage Analytics Methods ==========
    
    @Override
    public Map<String, Object> generateEquipmentUsageAnalyticsReport() {
        IMachineService service = getMachineService();
        if (service == null) {
            return Collections.singletonMap("error", "Machine service not available");
        }
        
        List<Machine> machines = service.getAllMachines();
        Map<String, Object> analytics = new HashMap<>();
        
        int totalMachines = machines.size();
        int totalCapacity = totalMachines * 8; // 8 bookings per machine
        int totalBookings = 0;
        
        Map<String, Integer> machineBookingCounts = new HashMap<>();
        for (Machine machine : machines) {
            Member[] bookings = machine.getBookings();
            int bookingCount = 0;
            for (Member booking : bookings) {
                if (booking != null) {
                    bookingCount++;
                }
            }
            totalBookings += bookingCount;
            machineBookingCounts.put(machine.getName() + " (ID: " + machine.getRegId() + ")", bookingCount);
        }
        
        double utilizationRate = totalCapacity > 0 ? (double) totalBookings / totalCapacity * 100 : 0.0;
        
        analytics.put("totalMachines", totalMachines);
        analytics.put("totalCapacity", totalCapacity);
        analytics.put("totalBookings", totalBookings);
        analytics.put("averageUtilizationRate", utilizationRate);
        analytics.put("machineBookingCounts", machineBookingCounts);
        analytics.put("generatedDate", LocalDate.now().toString());
        
        return analytics;
    }
    
    // ========== Export Methods ==========
    
    @Override
    public byte[] exportReportToTxt(String reportContent, String reportType) {
        if (reportContent == null) {
            return new byte[0];
        }
        String header = "=== " + reportType + " Report ===\n";
        String footer = "\n=== End of Report ===\n";
        String fullReport = header + reportContent + footer;
        return fullReport.getBytes();
    }
    
    @Override
    public byte[] exportReportToJson(Object reportData, String reportType) {
        // Simple JSON serialization (in production, use Jackson or Gson)
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"reportType\": \"").append(reportType).append("\",\n");
        json.append("  \"generatedDate\": \"").append(LocalDate.now()).append("\",\n");
        json.append("  \"data\": ");
        
        if (reportData instanceof Map) {
            json.append(mapToJson((Map<?, ?>) reportData));
        } else {
            json.append("\"").append(reportData.toString()).append("\"");
        }
        
        json.append("\n}");
        return json.toString().getBytes();
    }
    
    @Override
    public byte[] exportReportToCsv(String reportContent, String reportType) {
        // Simple CSV export (in production, use Apache Commons CSV)
        StringBuilder csv = new StringBuilder();
        csv.append("Report Type,Generated Date\n");
        csv.append(reportType).append(",").append(LocalDate.now()).append("\n\n");
        csv.append("Report Content\n");
        csv.append(reportContent.replace("\n", ","));
        return csv.toString().getBytes();
    }
    
    /**
     * Helper method to convert Map to JSON string
     */
    private String mapToJson(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                json.append(",\n");
            }
            json.append("    \"").append(entry.getKey()).append("\": ");
            Object value = entry.getValue();
            if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else if (value instanceof Map) {
                json.append(mapToJson((Map<?, ?>) value));
            } else {
                json.append(value);
            }
            first = false;
        }
        json.append("\n  }");
        return json.toString();
    }
}

