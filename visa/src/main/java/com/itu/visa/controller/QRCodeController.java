package com.itu.visa.controller;

import com.google.zxing.WriterException;
import com.itu.visa.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping("/demande/{id}")
    public ResponseEntity<byte[]> getQRCode(@PathVariable int id) {
        try {
            byte[] qrCodeImage = qrCodeService.generateQRCodeAsBytes(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
