package com.example.InvoiceService.repository;

import com.example.InvoiceService.model.ProjectInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectInvoiceRepository extends JpaRepository<ProjectInvoice, Long> {
}
