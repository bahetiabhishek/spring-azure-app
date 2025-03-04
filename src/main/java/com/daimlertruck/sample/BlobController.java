package com.daimlertruck.sample;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blob")
public class BlobController {

    private final AzureBlobService azureBlobService;

    public BlobController(AzureBlobService azureBlobService) {
        this.azureBlobService = azureBlobService;
    }

    @GetMapping("/download/{blobName}")
    public ResponseEntity<byte[]> downloadBlob(@PathVariable String blobName) {
        byte[] data = azureBlobService.downloadBlob(blobName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + blobName);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}

