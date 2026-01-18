package com.gymmanagement.base.entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Date Entity
 * Custom date class for the gym management system
 */
public class Date implements Serializable {
    private static final long serialVersionUID = 1L;

    private int day;
    private int month;
    private int year;

    public Date() {
        day = 0;
        month = 0;
        year = 0;
    }

    public Date(int theMonth, int theDay, int theYear) {
        month = theMonth;
        day = theDay;
        year = theYear;
    }

    public boolean checkDay(int testDay, int month) {
        int[] daysofmonth = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (testDay > 0 && testDay <= daysofmonth[month]) {
            return true;
        } else if (month == 2 && testDay == 29 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
            return true;
        }
        return false;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }
}
