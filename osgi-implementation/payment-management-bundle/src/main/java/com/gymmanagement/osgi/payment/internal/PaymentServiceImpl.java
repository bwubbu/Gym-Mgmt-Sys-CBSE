package com.gymmanagement.osgi.payment.internal;

import com.gymmanagement.osgi.base.entity.Member;
import com.gymmanagement.osgi.base.entity.Payment;
import com.gymmanagement.osgi.base.service.IMemberService;
import com.gymmanagement.osgi.base.service.IPaymentService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OSGi service implementation for Payment Management
 *
 * Implements:
 * - UC-13: Process Financial Transaction
 * - UC-14: Manage Outstanding Balances
 * - UC-15: Generate Financial Reports (members filtering)
 * - UC-16: Analyze Payment Analytics
 *
 * Note: Service registration is handled via XML descriptor:
 * OSGI-INF/com.gymmanagement.osgi.payment.service.xml
 */
public class PaymentServiceImpl implements IPaymentService {

    private IMemberService memberService;

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

    // ========== UC-14 Flow B: Add Outstanding Balance to Specific Member ==========

    @Override
    public String addOutstandingBalance(int memberId, double amount) {
        IMemberService service = getMemberService();
        if (service == null) {
            return "Error: Member service not available";
        }

        // Validate registration ID exists
        Member member = service.getMember(memberId);
        if (member == null) {
            return "Member not found.";
        }

        // Validate amount is positive number
        if (amount <= 0) {
            return "Balance should be in numbers.";
        }

        // Get or create payment object
        Payment payment = member.getPayment();
        if (payment == null) {
            payment = new Payment();
            member.setPayment(payment);
        }

        // Add balance
        payment.addOutstandingBalance(amount);
        service.updateMember(member);

        return "Balance Added Successfully.";
    }

    // ========== UC-14 Flow A: Add Outstanding Balance to All Members ==========

    @Override
    public String addOutstandingBalanceToAll(double amount) {
        IMemberService service = getMemberService();
        if (service == null) {
            return "Error: Member service not available";
        }

        List<Member> members = service.getAllMembers();

        // Check if there are members in the system
        if (members.isEmpty()) {
            return "No members found in the system.";
        }

        // Validate amount is positive number
        if (amount <= 0) {
            return "Balance should be in numbers.";
        }

        // Add balance to all members
        for (Member member : members) {
            Payment payment = member.getPayment();
            if (payment == null) {
                payment = new Payment();
                member.setPayment(payment);
            }
            payment.addOutstandingBalance(amount);
            service.updateMember(member);
        }

        return "Balance Added Successfully to All.";
    }

    // ========== UC-13: Process Financial Transaction (Pay Outstanding Balance) ==========

    @Override
    public String payOutstandingBalance(int memberId, double amount) {
        IMemberService service = getMemberService();
        if (service == null) {
            return "Error: Member service not available";
        }

        // Validate member exists
        Member member = service.getMember(memberId);
        if (member == null) {
            return "Member not found.";
        }

        // Get payment object
        Payment payment = member.getPayment();
        if (payment == null) {
            return "Member has no outstanding balance to pay.";
        }

        // Check if member has outstanding balance
        if (payment.getOutstandingBalance() == 0) {
            return "Member has no outstanding balance to pay.";
        }

        // Validate amount is positive
        if (amount <= 0) {
            return "Amount should be in numbers.";
        }

        // Process payment (Payment.payOutstandingBalance handles exceed limit check)
        String result = payment.payOutstandingBalance(amount);

        // If successful, update member in service
        if (result.equals("Amount Paid Successfully.")) {
            service.updateMember(member);
        }

        return result;
    }

    // ========== Get Payment for Member ==========

    @Override
    public Payment getPayment(int memberId) {
        IMemberService service = getMemberService();
        if (service == null) {
            return null;
        }

        Member member = service.getMember(memberId);
        if (member == null) {
            return null;
        }

        return member.getPayment();
    }

    // ========== UC-15: Generate Financial Reports - Members with Outstanding Balance ==========

    @Override
    public List<Member> getMembersWithOutstandingBalance() {
        IMemberService service = getMemberService();
        if (service == null) {
            return new ArrayList<>();
        }

        return service.getAllMembers().stream()
                .filter(m -> m.getPayment() != null &&
                        m.getPayment().getOutstandingBalance() > 0)
                .collect(Collectors.toList());
    }

    // ========== UC-15: Generate Financial Reports - Members with Zero Balance ==========

    @Override
    public List<Member> getMembersWithZeroBalance() {
        IMemberService service = getMemberService();
        if (service == null) {
            return new ArrayList<>();
        }

        return service.getAllMembers().stream()
                .filter(m -> m.getPayment() != null &&
                        m.getPayment().getOutstandingBalance() == 0)
                .collect(Collectors.toList());
    }

    // ========== UC-16: Analyze Payment Analytics ==========

    @Override
    public Map<String, Object> generatePaymentAnalytics() {
        IMemberService service = getMemberService();
        Map<String, Object> analytics = new LinkedHashMap<>();

        if (service == null) {
            analytics.put("error", "Member service not available");
            return analytics;
        }

        List<Member> members = service.getAllMembers();

        // Calculate totals
        double totalOutstanding = 0.0;
        int membersWithBalance = 0;
        int membersWithZeroBalance = 0;
        int totalMembers = members.size();

        // Member details list for table (sorted by outstanding balance descending)
        List<Map<String, Object>> memberDetails = new ArrayList<>();

        for (Member member : members) {
            Payment payment = member.getPayment();
            double balance = (payment != null) ? payment.getOutstandingBalance() : 0.0;
            String status = (payment != null) ? payment.checkStatus() : "Paid";

            totalOutstanding += balance;
            if (balance > 0) {
                membersWithBalance++;
            } else {
                membersWithZeroBalance++;
            }

            Map<String, Object> memberInfo = new LinkedHashMap<>();
            memberInfo.put("name", member.getName());
            memberInfo.put("regId", member.getRegId());
            memberInfo.put("outstandingBalance", balance);
            memberInfo.put("status", status);
            memberDetails.add(memberInfo);
        }

        // Sort by outstanding balance descending (highest balance first)
        memberDetails.sort((a, b) ->
                Double.compare((Double) b.get("outstandingBalance"),
                        (Double) a.get("outstandingBalance")));

        // Calculate average
        double averageOutstanding = totalMembers > 0 ?
                totalOutstanding / totalMembers : 0.0;

        // Populate analytics map
        analytics.put("totalOutstandingBalance", totalOutstanding);
        analytics.put("averageOutstandingBalance", averageOutstanding);
        analytics.put("membersWithOutstandingBalance", membersWithBalance);
        analytics.put("membersWithZeroBalance", membersWithZeroBalance);
        analytics.put("totalMembers", totalMembers);
        analytics.put("memberDetails", memberDetails);
        analytics.put("generatedDate", LocalDate.now().toString());

        return analytics;
    }

    /**
     * Generate payment analytics as formatted string report
     * Matches the format of UC-16 requirements
     *
     * @return Formatted payment analytics report string
     */
    public String generatePaymentAnalyticsReport() {
        Map<String, Object> analytics = generatePaymentAnalytics();

        if (analytics.containsKey("error")) {
            return "Error: " + analytics.get("error");
        }

        StringBuilder report = new StringBuilder();
        LocalDate currentDate = LocalDate.now();

        report.append("\n Date : ").append(currentDate).append("\n\n");
        report.append(" >>> Payment Analytics Report\n\n");

        // Summary section
        report.append("Payment Analytics Summary:\n");
        report.append("  Total Outstanding Balance: $")
                .append(String.format("%.2f", analytics.get("totalOutstandingBalance")))
                .append("\n");
        report.append("  Average Outstanding Balance: $")
                .append(String.format("%.2f", analytics.get("averageOutstandingBalance")))
                .append("\n");
        report.append("  Members with Outstanding Balance: ")
                .append(analytics.get("membersWithOutstandingBalance")).append("\n");
        report.append("  Members with Zero Balance: ")
                .append(analytics.get("membersWithZeroBalance")).append("\n");
        report.append("  Total Number of Members: ")
                .append(analytics.get("totalMembers")).append("\n\n");

        // Member table header
        report.append("---------------------------------------------------------------\n");
        report.append(String.format("%-20s %-15s %-20s %-15s\n",
                "Member Name", "Registration ID", "Outstanding Balance", "Payment Status"));
        report.append("---------------------------------------------------------------\n");

        // Member table rows
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> memberDetails =
                (List<Map<String, Object>>) analytics.get("memberDetails");

        for (Map<String, Object> member : memberDetails) {
            String name = (String) member.get("name");
            if (name != null && name.length() > 18) {
                name = name.substring(0, 18) + "..";
            }
            report.append(String.format("%-20s %-15d $%-19.2f %-15s\n",
                    name != null ? name : "Unknown",
                    member.get("regId"),
                    member.get("outstandingBalance"),
                    member.get("status")));
        }

        report.append("---------------------------------------------------------------\n");

        return report.toString();
    }
}
