//package com.example.InvoiceService.service;
//
//import com.example.InvoiceService.DTO.EmployeeDto;
//import com.example.InvoiceService.model.EmployeeInvoice;
//import com.example.InvoiceService.repository.EmployeeInvoiceRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//import java.math.BigDecimal;
//
//@Service
//public class EmployeeInvoiceService {
//
//    private final WebClient.Builder webClientBuilder;
//    private final EmployeeInvoiceRepository repository;
//
//    public EmployeeInvoiceService(WebClient.Builder webClientBuilder, EmployeeInvoiceRepository repository) {
//        this.webClientBuilder = webClientBuilder;
//        this.repository = repository;
//    }
//
////    // Generate Invoice for Employee
////    public EmployeeInvoice generateInvoice(Long employeeId) {
////        // Fetch employee from EmployeeService using WebClient
////        EmployeeDto employee = webClientBuilder.build()
////                .get()
////                .uri("http://localhost:8083/api/employees/{id}" + employeeId)  // Make sure employee-service is accessible
////                .retrieve()
////                .bodyToMono(EmployeeDto.class)
////                .block();  // Blocking here for simplicity, consider reactive chaining for non-blocking
////
////        if (employee == null) {
////            throw new RuntimeException("Employee not found");
////        }
////
////        // Calculate the invoice amount
////        BigDecimal hourlyRate = employee.getHourlyRate();
////        int workingHours = employee.getWorkingHours();
////        BigDecimal totalAmount = hourlyRate.multiply(BigDecimal.valueOf(workingHours));
////        BigDecimal taxAmount = totalAmount.multiply(BigDecimal.valueOf(0.1));  // Assuming 10% tax
////        BigDecimal finalAmount = totalAmount.add(taxAmount);
////
////        // Create and save the invoice
////        EmployeeInvoice invoice = new EmployeeInvoice();
////        invoice.setEmployeeId(employeeId);
////        invoice.setEmployeeName(employee.getName());
////        invoice.setTotalAmount(totalAmount);
////        invoice.setTaxAmount(taxAmount);
////        invoice.setFinalAmount(finalAmount);
////
////        return repository.save(invoice);
////    }
//
//    //    public EmployeeInvoice generateInvoice(Long employeeId) {
////        // Fetch employee from EmployeeService using WebClient
////        EmployeeDto employee = webClientBuilder.build()
////                .get()
////                .uri("http://localhost:8083/api/employees/{id}", employeeId)  // Correct URI format
////                .retrieve()
////                .bodyToMono(EmployeeDto.class)
////                .block();  // Blocking here for simplicity, consider reactive chaining for non-blocking
////
////        // If employee is null, throw an exception
////        if (employee == null) {
////            throw new RuntimeException("Employee not found");
////        }
////
////        // Calculate the invoice amount
////        BigDecimal hourlyRate = employee.getHourlyRate();
////        int workingHours = employee.getWorkingHours();
////        BigDecimal totalAmount = hourlyRate.multiply(BigDecimal.valueOf(workingHours));
////        BigDecimal taxAmount = totalAmount.multiply(BigDecimal.valueOf(0.1));  // Assuming 10% tax
////        BigDecimal finalAmount = totalAmount.add(taxAmount);
////
////        // Create and save the invoice
////        EmployeeInvoice invoice = new EmployeeInvoice();
////        invoice.setEmployeeId(employeeId);
////        invoice.setEmployeeName(employee.getName());  // Assuming employee has 'name' field
////        invoice.setTotalAmount(totalAmount);
////        invoice.setTaxAmount(taxAmount);
////        invoice.setFinalAmount(finalAmount);
////
////        return repository.save(invoice);  // Save the invoice to the repository
////    }
//    public EmployeeInvoice generateInvoice(Long employeeId) {
//        // Fetch employee from EmployeeService using WebClient
//        EmployeeDto employee = webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8083/api/employees/{id}", employeeId)
//                .retrieve()
//                .bodyToMono(EmployeeDto.class)
//                .block();  // Block for simplicity
//
//        if (employee == null) {
//            throw new RuntimeException("Employee not found");
//        }
//
//        // Calculate the invoice amounts
//        BigDecimal hourlyRate = employee.getHourlyRate();
//        int workingHours = employee.getWorkingHours();
//        BigDecimal totalAmount = hourlyRate.multiply(BigDecimal.valueOf(workingHours));
//        BigDecimal taxAmount = totalAmount.multiply(BigDecimal.valueOf(0.1));  // Assuming 10% tax
//        BigDecimal finalAmount = totalAmount.add(taxAmount);
//
//        // Calculate gross amount (totalAmount + taxAmount)
//        BigDecimal grossAmount = totalAmount.add(taxAmount);
//
//        // Create and save the invoice
//        EmployeeInvoice invoice = new EmployeeInvoice();
//        invoice.setEmployeeId(employeeId);
//        invoice.setEmployeeName(employee.getName());
//        invoice.setTotalAmount(totalAmount);
//        invoice.setTaxAmount(taxAmount);
//        invoice.setFinalAmount(finalAmount);
//        invoice.setGrossAmount(grossAmount);
//        // Set the missing grossAmount field
//        invoice.setBankName(employee.getBankName());
//        invoice.setPanNumber(employee.getPanNumber());
//        invoice.setBankAccountNumber(employee.getBankAccountNumber());
//
//        return repository.save(invoice);
//    }
//
//
//}
////














//package com.example.InvoiceService.service;
//
//import com.example.InvoiceService.DTO.EmployeeDto;
//import com.example.InvoiceService.model.EmployeeInvoice;
//import com.example.InvoiceService.repository.EmployeeInvoiceRepository;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.io.ByteArrayOutputStream;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//
//@Service
//public class EmployeeInvoiceService {
//
//    private final WebClient.Builder webClientBuilder;
//    private final EmployeeInvoiceRepository repository;
//
//    public EmployeeInvoiceService(WebClient.Builder webClientBuilder, EmployeeInvoiceRepository repository) {
//        this.webClientBuilder = webClientBuilder;
//        this.repository = repository;
//    }
//
//    public EmployeeInvoice generateInvoice(Long employeeId) {
//        EmployeeDto employee = webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8083/api/employees/{id}", employeeId)
//                .retrieve()
//                .bodyToMono(EmployeeDto.class)
//                .block();
//
//        if (employee == null) {
//            throw new RuntimeException("Employee not found");
//        }
//
//        BigDecimal hourlyRate = employee.getHourlyRate();
//        int workingHours = employee.getWorkingHours();
//        BigDecimal totalAmount = hourlyRate.multiply(BigDecimal.valueOf(workingHours));
//        BigDecimal taxAmount = totalAmount.multiply(BigDecimal.valueOf(0.1));
//        BigDecimal finalAmount = totalAmount.add(taxAmount);
//
//        EmployeeInvoice invoice = new EmployeeInvoice();
//        invoice.setEmployeeId(employeeId);
//        invoice.setEmployeeName(employee.getName());
//        invoice.setTotalAmount(totalAmount);
//        invoice.setTaxAmount(taxAmount);
//        invoice.setFinalAmount(finalAmount);
//        invoice.setGrossAmount(totalAmount.add(taxAmount));
//        invoice.setBankName(employee.getBankName());
//        invoice.setPanNumber(employee.getPanNumber());
//        invoice.setBankAccountNumber(employee.getBankAccountNumber());
//
//        return repository.save(invoice);
//    }
//
//    public byte[] generateInvoicePdf(Long employeeId) {
//        EmployeeInvoice invoice = generateInvoice(employeeId);
//
//        try (PDDocument document = new PDDocument(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
//                contentStream.beginText();
//                contentStream.setLeading(20);
//                contentStream.newLineAtOffset(50, 750);
//
//                contentStream.showText("Invoice Slip for Employee");
//                contentStream.newLine();
//                contentStream.setFont(PDType1Font.HELVETICA, 12);
//                contentStream.showText("Invoice Date: " + LocalDate.now());
//                contentStream.newLine();
//                contentStream.newLine();
//
//                contentStream.showText("Employee Details:");
//                contentStream.newLine();
//                contentStream.showText("Employee ID: " + invoice.getEmployeeId());
//                contentStream.newLine();
//                contentStream.showText("Employee Name: " + invoice.getEmployeeName());
//                contentStream.newLine();
//                contentStream.showText("Bank Name: " + invoice.getBankName());
//                contentStream.newLine();
//                contentStream.showText("Bank Account Number: " + invoice.getBankAccountNumber());
//                contentStream.newLine();
//                contentStream.showText("PAN Number: " + invoice.getPanNumber());
//                contentStream.newLine();
//                contentStream.newLine();
//
//                contentStream.showText("Payment Details:");
//                contentStream.newLine();
//                contentStream.showText("Total Amount (before tax): " + invoice.getTotalAmount());
//                contentStream.newLine();
//                contentStream.showText("Tax Amount (10%): " + invoice.getTaxAmount());
//                contentStream.newLine();
//                contentStream.showText("Final Amount (including tax): " + invoice.getFinalAmount());
//                contentStream.newLine();
//                contentStream.newLine();
//
//                contentStream.showText("Gross Amount: " + invoice.getGrossAmount());
//                contentStream.newLine();
//
//                contentStream.endText();
//            }
//
//            document.save(outputStream);
//            return outputStream.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate PDF", e);
//        }
//    }
//}
