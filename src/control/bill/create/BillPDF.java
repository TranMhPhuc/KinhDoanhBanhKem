package control.bill.create;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bill.BillModelInterface;
import util.constant.AppConstant;

public class BillPDF {

    private static BaseFont BASE_FONT;

    private static Font FONT_BMS_TITLE;
    private static Font FONT_ADDRESS_TITLE;
    private static Font FONT_MOBILE_TITLE;
    private static Font FONT_BILL_TITLE;

    static {
        try {
            BASE_FONT = BaseFont.createFont("C:\\windows\\fonts\\segoeui.ttf", 
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(BillPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BillPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        FONT_BMS_TITLE = new Font(BASE_FONT, 22, Font.BOLD);
        FONT_ADDRESS_TITLE = new Font(BASE_FONT, 15, Font.ITALIC);
        FONT_MOBILE_TITLE = new Font(BASE_FONT, 15, Font.NORMAL);
        FONT_BILL_TITLE = new Font(BASE_FONT, 18, Font.BOLD);
    }

    private BillPDF() {
    }

    private static void addEmptyLine(Document document, int spaceNumber) throws DocumentException {
        for (int i = 0; i < spaceNumber; i++) {
            document.add(new Paragraph(" "));
        }
    }

    public static void exportBill(BillModelInterface bill, String path) throws DocumentException, 
            FileNotFoundException {
        
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

        addEmptyLine(document, 1);

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

        Paragraph timeTitle = new Paragraph(billDateText, FONT_MOBILE_TITLE);
        timeTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(timeTitle);

        String billIDText = String.format("Mã hóa đơn: %s", bill.getBillIDText());

        Paragraph billIDTitle = new Paragraph(billIDText, FONT_MOBILE_TITLE);
        billIDTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(billIDTitle);

        // END
        document.close();
    }

}
