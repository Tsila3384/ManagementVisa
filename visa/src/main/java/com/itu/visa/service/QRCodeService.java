package com.itu.visa.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    @Value("${app.base-url:http://192.168.0.197:8080}")
    private String baseUrl;

    public byte[] generateQRCodeAsBytes(int idDemandeVisa) throws WriterException, IOException {
        System.out.println("Generating QR Code for demande ID: " + idDemandeVisa);
        System.out.println("Base URL: " + baseUrl);

        String url = baseUrl + "/demandes/" + idDemandeVisa;
        System.out.println("QR Code URL: " + url);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 300, 300, hints);

        // Retourne les bytes de l'image au lieu de sauvegarder en fichier
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

        return outputStream.toByteArray();
    }
}
