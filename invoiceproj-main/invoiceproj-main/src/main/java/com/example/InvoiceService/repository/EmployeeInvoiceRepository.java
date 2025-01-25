package com.example.InvoiceService.repository;

import com.example.InvoiceService.model.EmployeeInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeInvoiceRepository extends JpaRepository<EmployeeInvoice, Long> {
    // Additional query methods can be defined here if needed
}
