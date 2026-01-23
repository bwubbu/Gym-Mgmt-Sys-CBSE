// package com.gymmanagement.base.entity;

// import java.io.Serializable;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class BodyStats implements Serializable {
//     private double height;
//     private double weight;
//     private double bmi;
//     private double bodyFatPercentage;
// }

// package com.gymmanagement.base.entity;

// import java.io.Serializable;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// public class BodyStats implements Serializable {
//     private static final long serialVersionUID = 1L;
//     private double height;
//     private double weight;
//     private double bmi;
//     private double bodyFatPercentage;
// }

package com.gymmanagement.base.entity;
import java.io.Serializable;

public class BodyStats implements Serializable {
    private static final long serialVersionUID = 1L;
    private double height;
    private double weight;
    private double bmi;
    private double bodyFatPercentage;

    public BodyStats() {}

    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }
    public double getBodyFatPercentage() { return bodyFatPercentage; }
    public void setBodyFatPercentage(double bodyFatPercentage) { this.bodyFatPercentage = bodyFatPercentage; }
}