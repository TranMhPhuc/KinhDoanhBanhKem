package control.bill.create;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillModelInterface;
import model.bill.detail.ProductDetailModelInterface;
import util.constant.AppConstant;

public class BillPDF {

    private static BaseFont BASE_FONT;

    private static Font FONT_BMS_TITLE;
    private static Font FONT_ADDRESS_TITLE;
    private static Font FONT_MOBILE_TITLE;
    private static Font FONT_BILL_TITLE;
    private static Font FONT_PRODUCT_DETAIL;
    private static Font FONT_MONEY_BILL;

    static {
        try {
            BASE_FONT = BaseFont.createFont("C:\\windows\\fonts\\segoeui.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(BillPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BillPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        FONT_BMS_TITLE = new Font(BASE_FONT, 26, Font.BOLD);
        FONT_ADDRESS_TITLE = new Font(BASE_FONT, 15, Font.ITALIC);
        FONT_MOBILE_TITLE = new Font(BASE_FONT, 15, Font.NORMAL);
        FONT_BILL_TITLE = new Font(BASE_FONT, 18, Font.BOLD);
        FONT_PRODUCT_DETAIL = new Font(BASE_FONT, 15, Font.NORMAL);
        FONT_MONEY_BILL = new Font(BASE_FONT, 15, Font.BOLD);
    }

    private BillPDF() {
    }

    private static void addEmptyLine(Document document, int spaceNumber) throws DocumentException {
        for (int i = 0; i < spaceNumber; i++) {
            document.add(new Paragraph(" "));
        }
    }

    public static void exportBill(BillModelInterface bill, Iterator<ProductDetailModelInterface> productDetails,
            String path) throws DocumentException, FileNotFoundException {

        if (bill == null) {
            throw new NullPointerException();
        }

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        document.newPage();

        Paragraph bmsTitle = new Paragraph("Bakery Management System", FONT_BMS_TITLE);
        bmsTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(bmsTitle);

        Paragraph addressTitle = new Paragraph(
                "169 Lê Văn Việt, Phường Hiệp Phú, TP Thủ Đức, TPHCM",
                FONT_ADDRESS_TITLE);
        addressTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(addressTitle);

        Paragraph mobileTitle = new Paragraph("Điện thoại: 0968 72 68 42",
                FONT_MOBILE_TITLE);
        mobileTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(mobileTitle);

        Paragraph seperatorLine = new Paragraph("------------------------",
                FONT_MOBILE_TITLE);
        seperatorLine.setAlignment(Element.ALIGN_CENTER);
        document.add(seperatorLine);

        addEmptyLine(document, 2);

        Paragraph billTitle = new Paragraph("Hóa đơn thanh toán", FONT_BILL_TITLE);
        billTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(billTitle);

        addEmptyLine(document, 1);

        // Ngày giờ
//        Timestamp exportDateTime = bill.getDateTimeExport();
        Timestamp exportDateTime = Timestamp.from(Instant.now());

        LocalDate billDate = exportDateTime.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalTime billTime = exportDateTime.toInstant().atZone(ZoneId.systemDefault())
                .toLocalTime();

        String billDateText = String.format("Ngày: %s", billDate
                .format(AppConstant.GLOBAL_DATE_FORMATTER));

        Paragraph dateTitle = new Paragraph(billDateText, FONT_MOBILE_TITLE);
        dateTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(dateTitle);

        String billTimeText = String.format("Thời gian: %s", billTime
                .format(AppConstant.GLOBAL_TIME_FORMATTER));

        Paragraph timeTitle = new Paragraph(billTimeText, FONT_MOBILE_TITLE);
        timeTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(timeTitle);

        String billIDText = String.format("Mã hóa đơn: %s", bill.getBillIDText());

        Paragraph billIDTitle = new Paragraph(billIDText, FONT_MOBILE_TITLE);
        billIDTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(billIDTitle);

        String employeeID = String.format("Mã nhân viên: %s", bill.getEmployee().getEmployeeIDText());

        Paragraph employeeIDTitle = new Paragraph(employeeID, FONT_MOBILE_TITLE);
        employeeIDTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(employeeIDTitle);

        String employeeName = String.format("Tên nhân viên: %s", bill.getEmployee().getName());

        Paragraph employeeNameTitle = new Paragraph(employeeName, FONT_MOBILE_TITLE);
        employeeNameTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(employeeNameTitle);

        addEmptyLine(document, 2);

        PdfPTable table = new PdfPTable(6);
        table.setWidths(new int[]{3, 4, 2, 2, 4, 4});

        PdfPCell c1 = new PdfPCell(new Phrase("Mã sản phẩm", FONT_PRODUCT_DETAIL));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBorder(Rectangle.BOTTOM);
        table.addCell(c1);

        PdfPCell c2 = new PdfPCell(new Phrase("Tên", FONT_PRODUCT_DETAIL));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        c2.setBorder(Rectangle.BOTTOM);
        table.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("Kích thước", FONT_PRODUCT_DETAIL));
        c3.setHorizontalAlignment(Element.ALIGN_CENTER);
        c3.setBorder(Rectangle.BOTTOM);
        table.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Số lượng", FONT_PRODUCT_DETAIL));
        c4.setHorizontalAlignment(Element.ALIGN_CENTER);
        c4.setBorder(Rectangle.BOTTOM);
        table.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Đơn giá", FONT_PRODUCT_DETAIL));
        c5.setHorizontalAlignment(Element.ALIGN_CENTER);
        c5.setBorder(Rectangle.BOTTOM);
        table.addCell(c5);
        
        PdfPCell c6 = new PdfPCell(new Phrase("Thành tiền", FONT_PRODUCT_DETAIL));
        c6.setHorizontalAlignment(Element.ALIGN_CENTER);
        c6.setBorder(Rectangle.BOTTOM);
        table.addCell(c6);

        table.setHeaderRows(1);

        while (productDetails.hasNext()) {
            ProductDetailModelInterface productDetail = productDetails.next();

            PdfPCell productIDCell = new PdfPCell(new Phrase(
                    productDetail.getProduct().getProductIDText(),
                    FONT_PRODUCT_DETAIL));
            productIDCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            productIDCell.setBorder(Rectangle.BOTTOM);

            table.addCell(productIDCell);

            PdfPCell productNameCell = new PdfPCell(new Phrase(
                    productDetail.getProduct().getName(),
                    FONT_PRODUCT_DETAIL));
            productNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            productNameCell.setBorder(Rectangle.BOTTOM);

            table.addCell(productNameCell);

            PdfPCell productSizeCell = new PdfPCell(new Phrase(
                    productDetail.getProduct().getSize().toString(),
                    FONT_PRODUCT_DETAIL));
            productSizeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            productSizeCell.setBorder(Rectangle.BOTTOM);

            table.addCell(productSizeCell);

            PdfPCell productAmountCell = new PdfPCell(new Phrase(
                    String.valueOf(productDetail.getAmount()),
                    FONT_PRODUCT_DETAIL));
            productAmountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            productAmountCell.setBorder(Rectangle.BOTTOM);

            table.addCell(productAmountCell);
            //--
            PdfPCell productUnitPrice = new PdfPCell(new Phrase(
                    AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER
                            .format(productDetail.getPrice() / productDetail.getAmount()),
                    FONT_PRODUCT_DETAIL));
            productUnitPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
            productUnitPrice.setBorder(Rectangle.BOTTOM);

            table.addCell(productUnitPrice);
            //--
            PdfPCell productPriceCell = new PdfPCell(new Phrase(
                    AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER
                            .format(productDetail.getPrice()),
                    FONT_PRODUCT_DETAIL));
            productPriceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            productPriceCell.setBorder(Rectangle.BOTTOM);

            table.addCell(productPriceCell);
        }

        document.add(table);

        addEmptyLine(document, 2);

        String totalMoney = String.format("Tổng tiền: %s", 
                AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getPayment()));

        Paragraph totalMoneyTitle = new Paragraph(totalMoney, FONT_MONEY_BILL);
        totalMoneyTitle.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalMoneyTitle);
        
        String guestMoney = String.format("Tiền mặt: %s", 
                AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getGuestMoney()));

        Paragraph guestMoneyTitle = new Paragraph(guestMoney, FONT_MOBILE_TITLE);
        guestMoneyTitle.setAlignment(Element.ALIGN_RIGHT);
        document.add(guestMoneyTitle);
        
        String changeMoney = String.format("Tiền thối lại: %s", 
                AppConstant.GLOBAL_VIE_CURRENCY_FORMATTER.format(bill.getChangeMoney()));

        Paragraph changeMoneyTitle = new Paragraph(changeMoney, FONT_MONEY_BILL);
        changeMoneyTitle.setAlignment(Element.ALIGN_RIGHT);
        document.add(changeMoneyTitle);
        
        addEmptyLine(document, 3);
        
        Paragraph goodbyeTitle = new Paragraph(
                "Cảm ơn quý khách và hẹn gặp lại!", FONT_ADDRESS_TITLE);
        goodbyeTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(goodbyeTitle);
        
        // END
        document.close();
    }

}
