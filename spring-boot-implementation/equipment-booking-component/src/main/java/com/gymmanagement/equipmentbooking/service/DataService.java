package com.gymmanagement.equipmentbooking.service;

import com.gymmanagement.base.entity.Member;
import com.gymmanagement.base.entity.Payment;
import com.gymmanagement.base.entity.BodyStats;
import com.gymmanagement.base.entity.MemberPlan;
import com.gymmanagement.base.entity.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * DataService
 * 
 * Responsible for fetching data from other components (Member Management).
 */
@Service
public class DataService {

    @Value("${services.member.url:http://localhost:8081}")
    private String memberServiceUrl;

    private final RestTemplate restTemplate;

    public DataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Member getMember(int memberId) {
        // Mocking for now as per reference implementation pattern
        Payment mockPayment = new Payment(0.0, "John Doe", "1234-5678-9012-3456");
        
        BodyStats stats = new BodyStats();
        stats.setHeight(5.9);
        stats.setWeight(75.0);
        
        return new Member(
            memberId, 
            "Mock Member", 
            "mock@gmail.com", 
            "0300-1234567", 
            "Mock Address", 
            new Date(1, 1, 2023), 
            new Date(1, 1, 1990), 
            33, 
            "Male", 
            5.9, 
            75.0, 
            mockPayment, 
            "Muscle Gain",
            stats,
            new MemberPlan(),
            null
        );
    }
}
