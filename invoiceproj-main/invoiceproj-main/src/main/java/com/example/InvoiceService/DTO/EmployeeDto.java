package com.example.InvoiceService.DTO;

import jakarta.persistence.ElementCollection;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmployeeDto {
    private Long id;
    private String name;
    private String designation;
    private String skills;
    private Boolean isAvailable;
    private LocalDate joiningDate;
    private String bankAccountNumber;

    private String panNumber;
    private String bankName;
    private Long currentSalary;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(Long currentSalary) {
        this.currentSalary = currentSalary;
    }

    public EmployeeDto(Long id, String name, String designation, String skills, Boolean isAvailable, LocalDate joiningDate, String bankAccountNumber, String panNumber, String bankName, Long currentSalary) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.skills = skills;
        this.isAvailable = isAvailable;
        this.joiningDate = joiningDate;
        this.bankAccountNumber = bankAccountNumber;
        this.panNumber = panNumber;
        this.bankName = bankName;
        this.currentSalary = currentSalary;
    }

    public EmployeeDto() {
    }
}
