package x.xx;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.barcodes.Barcode1D;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.font.FontProvider;

import java.io.*;

public class Pdf {
    void html2pdf(File htmlFile, File pdfFile) throws IOException {
        ConverterProperties converterProperties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
        PdfFont font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",true);
        fontProvider.addFont(font.getFontProgram(), "UniGB-UCS2-H");
        converterProperties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new FileInputStream(htmlFile),
                new FileOutputStream(pdfFile), converterProperties);
    }
    public static void main(String[] args) throws IOException {
//        Barcode1D barcode = new Barcode128(new PdfDocument());
//        barcode.setCodeType(Barcode128.CODE128);
//        barcode.setCode("9781935182610");
//        barcode.setTextAlignment(Barcode1D.ALIGN_LEFT);
//        barcode.placeBarcode(new Canvas(), ColorConstants.BLACK, ColorConstants.BLACK);


        //         https://github.com/itext/itextpdf
        // example:  https://kb.itextpdf.com/itext/multiple-images
    }


    //
    public static void addImage() throws FileNotFoundException {
        String dest = "path";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Image image = new Image(ImageDataFactory.create(baos.toByteArray()));
        Document doc = new Document(pdfDoc);
        doc.add(image);
        // Image image = Image.getInstance(skuOutputStream.toByteArray());
        // image.setRotationDegrees(90);
    }
}
