package com.counterly.order;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/ocr")
public class OCRController {

    private final ITesseract tesseract;

    public OCRController() {
        this.tesseract = new Tesseract();
        // Set the tessdata path where language files are stored
        this.tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // Adjust for your OS
        this.tesseract.setLanguage("eng"); // English OCR
    }

    @PostMapping(value = "/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String extractText(@RequestParam("file") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("ocr-", file.getOriginalFilename());
        file.transferTo(tempFile);



        try {
            String text = tesseract.doOCR(tempFile); // Extract text

            // 🔎 Regex-based extraction (generic, not hardcoded)
            String clientLastName     = findField(text, "Apelyido[:\\s]*([A-Za-z]+)");
            String clientFirstName    = findField(text, "Unang Pangalan[:\\s]*([A-Za-z]+)");
            String clientMiddleName   = findField(text, "Gitnang Apelyido[:\\s]*([A-Za-z]+)");
            String address            = findField(text, "Tirahan[:\\s]*([A-Za-z0-9 ,.-]+)");
            String dob                = findField(text, "(?:Ipinanganak|Kapanganakan|Date of Birth)[:\\s]*([0-9/\\-]+)");
            String age                = findField(text, "Edad[:\\s]*([0-9]+)");
            String gender             = findField(text, "Kasarian[:\\s]*(Male|Female|Lalake|Babae)");
            String civilStatus        = findField(text, "Civil Status[:\\s]*(Single|Married|Widow|Separated|Widower)");
            String beneficiaryLast    = findField(text, "Apelyido ng Benepisyaryo[:\\s]*([A-Za-z]+)");
            String beneficiaryFirst   = findField(text, "Unang Pangalan ng Benepisyaryo[:\\s]*([A-Za-z]+)");

            String output = "clientLastName : " + clientLastName + ", ";
            output += "clientFirstName : " + clientFirstName + ", ";
            output += "clientMiddleName : " + clientMiddleName + ", ";
            return output;
        } catch (TesseractException e) {
            throw new RuntimeException("OCR failed: " + e.getMessage(), e);
        } finally {
            tempFile.delete();
        }
    }

    private String findField(String text, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
}
