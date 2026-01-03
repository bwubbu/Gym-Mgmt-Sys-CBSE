package com.gymmanagement.reportanalytics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Report Entity
 * 
 * This entity represents a generated report in the system.
 * According to the component architecture, this entity belongs to the Report Analytics Component.
 */
@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "report_type", nullable = false)
    private String reportType;
    
    @Column(name = "report_content", columnDefinition = "TEXT")
    private String reportContent;
    
    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;
    
    @Column(name = "generated_by")
    private String generatedBy; // Admin username
    
    @Column(name = "export_format")
    private String exportFormat; // TXT, JSON, CSV
    
    @Column(name = "file_path")
    private String filePath; // Path to exported file if exported
    
    @PrePersist
    protected void onCreate() {
        generatedAt = LocalDateTime.now();
    }
}

