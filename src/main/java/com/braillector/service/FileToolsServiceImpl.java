package com.braillector.service;

import com.braillector.dto.ResponseDTO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileToolsServiceImpl implements IFileToolsService {
    @Value("${braillector-backend.file.valid-extensions}")
    String validExtensions;

    @Value("${braillector-backend.file.max-size-mb}")
    long maxSizeMb;

    private final IBraillectorService braillectorService;

    @Autowired
    public FileToolsServiceImpl(
        IBraillectorService braillectorService
    ) {
        this.braillectorService = braillectorService;
    }


    @Override
    public String checkExtensionFile(MultipartFile file) throws Exception {
        final List<String> allowedExtensions = Arrays.asList(validExtensions.split(",\\s*"));
        final String fileOriginalName = file.getOriginalFilename();

        return allowedExtensions
            .stream()
            .filter(fileOriginalName::endsWith)
            .findFirst()
            .orElseThrow(() -> new Exception("Only txt, docx and pdf are supported"));
    }

    @Override
    public void checkMaxSize(MultipartFile file) throws Exception {
        final long fileSize = file.getSize();
        final long maxFileSize = maxSizeMb * 1024 * 1024;

        if (fileSize > maxFileSize) {
            throw new Exception("File is too big");
        }
    }

    @Override
    public List<String> readLines(MultipartFile file, String extensionFile) throws Exception {
        List<String> lines;

        switch (extensionFile) {
            case ".txt":
                InputStream is = file.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                lines = br.lines().collect(Collectors.toList());
                break;
            case ".pdf":
                PDDocument document = PDDocument.load(file.getInputStream());
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                lines = List.of(text.split("\\r?\\n"));
                break;
            case ".docx":
                lines = new XWPFDocument(file.getInputStream())
                    .getParagraphs()
                    .stream()
                    .map(XWPFParagraph::getText)
                    .collect(Collectors.toUnmodifiableList());
                break;
            default:
                throw new IllegalArgumentException("FORMATO DE ARCHIVO NO SOPORTADO, Archivos soportados " + validExtensions);
        }

        return lines;
    }

    @Override
    public byte[] linesToBytes(List<String> lines) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            for (String line : lines) {
                System.out.println(line);
                final ResponseDTO convertedLine = braillectorService.textoABraille(line);
                outputStream.write((convertedLine.getMessage() + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            throw new Exception("Error al generar el archivo");
        }

        return outputStream.toByteArray();
    }
}
