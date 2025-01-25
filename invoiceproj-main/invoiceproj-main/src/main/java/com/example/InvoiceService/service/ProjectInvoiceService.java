package com.example.InvoiceService.service;

import com.example.InvoiceService.DTO.ProjectDTO;
import com.example.InvoiceService.model.ProjectInvoice;
import com.example.InvoiceService.repository.ProjectInvoiceRepository;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ProjectInvoiceService {

    private final ProjectInvoiceRepository projectInvoiceRepository;
    private final WebClient webClient;
    private final String projectServiceUrl;

    public ProjectInvoiceService(ProjectInvoiceRepository projectInvoiceRepository, WebClient.Builder webClientBuilder) {
        this.projectInvoiceRepository = projectInvoiceRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:3031/api/projects/").build();
        this.projectServiceUrl = "{id}";
    }

    public ProjectInvoice generateInvoice(Long projectId) {
        // Fetch project details
        ProjectDTO projectDTO = getProjectDetails(projectId);

        // Create or retrieve project invoice
        return saveOrUpdateProjectInvoice(projectDTO);
    }

    public byte[] generateInvoicePdf(Long projectId) {
        // Generate the invoice using the projectId
        ProjectInvoice invoice = generateInvoice(projectId);

        // Create PDF content using invoice data
        return createPdf(invoice);
    }

    private ProjectDTO getProjectDetails(Long projectId) {
        // Using WebClient to fetch project details
        ProjectDTO projectDTO = webClient.get()
                .uri(projectServiceUrl, projectId)
                .retrieve()
                .bodyToMono(ProjectDTO.class)
                .block();
        if (projectDTO == null) {
            throw new RuntimeException("Project not found");
        }
        return projectDTO;
    }

    private ProjectInvoice saveOrUpdateProjectInvoice(ProjectDTO projectDTO) {
        Optional<ProjectInvoice> existingInvoice = projectInvoiceRepository.findById(projectDTO.getId());

        if (existingInvoice.isPresent()) {
            return existingInvoice.get();
        }

        // Create a new project invoice
        ProjectInvoice newInvoice = new ProjectInvoice();
        newInvoice.setInvoiceId(generateInvoiceId(projectDTO.getId()));
        newInvoice.setProjectId(projectDTO.getId());
        newInvoice.setClientName(projectDTO.getClientName());
        newInvoice.setPoNumber("PO-" + projectDTO.getId());
        newInvoice.setTotalAmount(BigDecimal.valueOf(projectDTO.getBudget()));
        newInvoice.setTaxAmount(calculateTax(BigDecimal.valueOf(projectDTO.getBudget())));
        newInvoice.setIssueDate(LocalDate.now());
        newInvoice.setDueDate(LocalDate.now().plusDays(30));

        return projectInvoiceRepository.save(newInvoice);
    }

    private String generateInvoiceId(Long projectId) {
        return "INV-" + projectId + "-" + System.currentTimeMillis();
    }

    private BigDecimal calculateTax(BigDecimal amount) {
        final BigDecimal taxRate = BigDecimal.valueOf(0.18); // 18% GST
        return amount.multiply(taxRate);
    }

    private byte[] createPdf(ProjectInvoice invoice) {
        try (PDDocument document = new PDDocument(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.setLeading(15);
                contentStream.newLineAtOffset(50, 750);

                // Invoice Header
                contentStream.showText("Project Invoice");
                contentStream.newLine();
                contentStream.showText("-----------------------------");
                contentStream.newLine();
                contentStream.showText("Invoice Number: " + invoice.getInvoiceId());
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

                // Project Details Section
                contentStream.showText("Project Details:");
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");
                contentStream.newLine();
                contentStream.showText("Project ID: " + invoice.getProjectId());
                contentStream.newLine();
                contentStream.showText("Client Name: " + invoice.getClientName());
                contentStream.newLine();
                contentStream.showText("PO Number: " + invoice.getPoNumber());
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
                contentStream.showText("Total Amount: " + invoice.getTotalAmount());
                contentStream.newLine();
                contentStream.showText("Tax Amount (18% GST): " + invoice.getTaxAmount());
                contentStream.newLine();
                contentStream.showText("Issue Date: " + invoice.getIssueDate());
                contentStream.newLine();
                contentStream.showText("Due Date: " + invoice.getDueDate());
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
                contentStream.showText("Thank you for your business!");
                contentStream.newLine();
                contentStream.showText("--------------------------------------------------------");

                contentStream.endText();
            }

            // Save PDF to byte array
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
