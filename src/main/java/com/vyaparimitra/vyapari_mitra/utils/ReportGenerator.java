package com.vyaparimitra.vyapari_mitra.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.vyaparimitra.vyapari_mitra.model.Customer;
import com.vyaparimitra.vyapari_mitra.model.Transaction;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String generateCustomerReport(List<Customer> customers, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            Paragraph title = new Paragraph("ग्राहक यादी अहवाल")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20);
            document.add(title);

            // Date
            Paragraph date = new Paragraph("दिनांक: " + LocalDate.now().format(DATE_FORMATTER))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(10);
            document.add(date);

            document.add(new Paragraph("\n"));

            // Create table
            Table table = new Table(UnitValue.createPercentArray(new float[]{5, 20, 15, 15, 15, 15, 15}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Add headers
            table.addHeaderCell(new Cell().add(new Paragraph("क्र.")));
            table.addHeaderCell(new Cell().add(new Paragraph("नाव")));
            table.addHeaderCell(new Cell().add(new Paragraph("मोबाईल")));
            table.addHeaderCell(new Cell().add(new Paragraph("गाव")));
            table.addHeaderCell(new Cell().add(new Paragraph("एकूण उधारी")));
            table.addHeaderCell(new Cell().add(new Paragraph("भरले")));
            table.addHeaderCell(new Cell().add(new Paragraph("बाकी")));

            // Add customer data
            int srNo = 1;
            for (Customer customer : customers) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(srNo++))));
                table.addCell(new Cell().add(new Paragraph(customer.getName())));
                table.addCell(new Cell().add(new Paragraph(customer.getMobile() != null ? customer.getMobile() : "-")));
                table.addCell(new Cell().add(new Paragraph(customer.getVillage() != null ? customer.getVillage() : "-")));
                table.addCell(new Cell().add(new Paragraph("₹" + customer.getTotalCredit().toString())));
                table.addCell(new Cell().add(new Paragraph("₹" + customer.getTotalPaid().toString())));
                table.addCell(new Cell().add(new Paragraph("₹" + customer.getBalance().toString())));
            }

            document.add(table);
            document.close();

            return filePath;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateTransactionReport(List<Transaction> transactions, String filePath) {
        try {
            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Title
            Paragraph title = new Paragraph("व्यवहार अहवाल")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20);
            document.add(title);

            // Date
            Paragraph date = new Paragraph("दिनांक: " + LocalDate.now().format(DATE_FORMATTER))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(10);
            document.add(date);

            document.add(new Paragraph("\n"));

            // Create table
            Table table = new Table(UnitValue.createPercentArray(new float[]{5, 15, 15, 15, 15, 20, 15}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Add headers
            table.addHeaderCell(new Cell().add(new Paragraph("क्र.")));
            table.addHeaderCell(new Cell().add(new Paragraph("ग्राहक")));
            table.addHeaderCell(new Cell().add(new Paragraph("प्रकार")));
            table.addHeaderCell(new Cell().add(new Paragraph("रक्कम")));
            table.addHeaderCell(new Cell().add(new Paragraph("तारीख")));
            table.addHeaderCell(new Cell().add(new Paragraph("शेवटची तारीख")));
            table.addHeaderCell(new Cell().add(new Paragraph("वर्णन")));

            // Add transaction data
            int srNo = 1;
            for (Transaction t : transactions) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(srNo++))));
                table.addCell(new Cell().add(new Paragraph(t.getCustomer().getName())));

                String typeMarathi = t.getType().equals("CREDIT") ? "उधारी" : "पैसे भरले";
                table.addCell(new Cell().add(new Paragraph(typeMarathi)));

                table.addCell(new Cell().add(new Paragraph("₹" + t.getAmount().toString())));
                table.addCell(new Cell().add(new Paragraph(t.getTransactionDate().format(DATE_FORMATTER))));

                String dueDate = t.getDueDate() != null ? t.getDueDate().format(DATE_FORMATTER) : "-";
                table.addCell(new Cell().add(new Paragraph(dueDate)));

                table.addCell(new Cell().add(new Paragraph(t.getDescription() != null ? t.getDescription() : "-")));
            }

            document.add(table);
            document.close();

            return filePath;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}