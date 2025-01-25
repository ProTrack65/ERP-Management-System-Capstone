package com.example.InvoiceService.service;

import com.example.InvoiceService.model.EmployeeInvoice;
import com.example.InvoiceService.DTO.EmployeeDto;
import com.example.InvoiceService.repository.EmployeeInvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.ByteArrayOutputStream;

@Service
public class EmployeeInvoiceService {
    private final WebClient.Builder webClientBuilder;
    private final EmployeeInvoiceRepository repository;

    public EmployeeInvoiceService(WebClient.Builder webClientBuilder, EmployeeInvoiceRepository repository) {
        this.webClientBuilder = webClientBuilder;
        this.repository = repository;
    }

    public EmployeeInvoice generateInvoice(Long employeeId) {
        EmployeeDto employee = webClientBuilder.build()
                .get()
                .uri("http://localhost:3030/api/employees/{id}", employeeId)
                .retrieve()
                .bodyToMono(EmployeeDto.class)
                .block();

        if (employee == null) {
            throw new RuntimeException("Employee not found");
        }

        BigDecimal annualSalary = BigDecimal.valueOf(employee.getCurrentSalary());
        BigDecimal monthlySalary = annualSalary.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal annualTax = annualSalary.multiply(BigDecimal.valueOf(0.2)); // Example 20% tax rate
        BigDecimal monthlyTax = annualTax.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal annualNetAmount = annualSalary.subtract(annualTax);
        BigDecimal monthlyNetAmount = monthlySalary.subtract(monthlyTax);

        EmployeeInvoice invoice = new EmployeeInvoice();
        invoice.setEmployeeId(employeeId);
        invoice.setEmployeeName(employee.getName());
        invoice.setTotalAmount(annualSalary);
        invoice.setTaxAmount(annualTax);
        invoice.setFinalAmount(annualNetAmount);
        invoice.setGrossAmount(annualSalary);
        invoice.setBankName(employee.getBankName());
        invoice.setPanNumber(employee.getPanNumber());
        invoice.setBankAccountNumber(employee.getBankAccountNumber());

        return repository.save(invoice);
    }

    public byte[] generateInvoicePdf(Long employeeId) {
        // Generate the invoice using the employeeId
        EmployeeInvoice invoice = generateInvoice(employeeId);

        // Extract necessary data for the PDF
        BigDecimal monthlySalary = invoice.getTotalAmount().divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal annualTax = invoice.getTaxAmount();
        BigDecimal monthlyTax = annualTax.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal monthlyNetAmount = monthlySalary.subtract(monthlyTax);

        try (PDDocument document = new PDDocument(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4); // A4 page size
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12); // Font for content
                contentStream.beginText();
                contentStream.setLeading(15); // Line spacing
                contentStream.newLineAtOffset(50, 750); // Start position

                // Invoice Header
                contentStream.showText("Employee Annual Invoice Slip");
                contentStream.newLine();
                contentStream.showText("-----------------------------");
                contentStream.newLine();
                contentStream.showText("Invoice Number: INV-" + invoice.getInvoiceId()); // Dynamic invoice ID
                contentStream.newLine();
                contentStream.showText("Invoice Date: " + LocalDate.now());
                contentStream.newLine();
                contentStream.showText("-----------------------------");
                contentStream.newLine();
                contentStream.newLine();

                // Company Information Section
                contentStream.showText("Company Name: DummyCorp");
                contentStream.newLine();
                contentStream.showText("Address: 456 Corporate Blvd, Springfield, IL, 62701");
                contentStream.newLine();
                contentStream.showText("Phone: +1 (800) 123-4567");
                contentStream.newLine();
                contentStream.showText("Email: contact@dummycorp.com");
                contentStream.newLine();
                contentStream.showText("Website: www.dummycorp.com");
                contentStream.newLine();
                contentStream.showText("-----------------------------");
                contentStream.newLine();
                contentStream.newLine();

                // Employee Details Section
                contentStream.showText("Employee Details:");
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.showText("Employee ID: " + invoice.getEmployeeId());
                contentStream.newLine();
                contentStream.showText("Employee Name: " + invoice.getEmployeeName());
                contentStream.newLine();
                contentStream.showText("Employee Address: 123 Main St, Springfield, IL, 62701"); // Example address
                contentStream.newLine();
                contentStream.showText("Bank Name: " + invoice.getBankName());
                contentStream.newLine();
                contentStream.showText("Bank Account Number: " + invoice.getBankAccountNumber());
                contentStream.newLine();
                contentStream.showText("PAN Number: " + invoice.getPanNumber());
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.newLine();

                // Payment Details Section
                contentStream.showText("Payment Details:");
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.showText("Annual Salary: " + invoice.getTotalAmount());
                contentStream.newLine();
                contentStream.showText("Annual Tax Amount (20%): " + invoice.getTaxAmount());
                contentStream.newLine();
                contentStream.showText("Net Annual Amount (after tax): " + invoice.getFinalAmount());
                contentStream.newLine();
                contentStream.showText("Gross Amount: " + invoice.getGrossAmount());
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.newLine();

                // Monthly Breakdown Section
                contentStream.showText("Monthly Breakdown:");
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.showText("Monthly Salary: " + monthlySalary);
                contentStream.newLine();
                contentStream.showText("Monthly Tax Amount: " + monthlyTax);
                contentStream.newLine();
                contentStream.showText("Net Monthly Amount (after tax): " + monthlyNetAmount);
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.newLine();

                // Closing Text
                contentStream.showText("For any inquiries, please contact:");
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.showText("Company Name: DummyCorp");
                contentStream.newLine();
                contentStream.showText("Phone: +1 (800) 123-4567");
                contentStream.newLine();
                contentStream.showText("Email: contact@dummycorp.com");
                contentStream.newLine();
                contentStream.showText("Website: www.dummycorp.com");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Thank you for your service!");
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");

                // End Text
                contentStream.endText();
            }

            // Save the generated PDF to a byte array
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
