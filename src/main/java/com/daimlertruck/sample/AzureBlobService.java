package com.daimlertruck.sample;

import com.azure.storage.blob.BlobClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class AzureBlobService {

    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.container-name}")
    private String containerName;

    private final AzureOAuth2Service azureOAuth2Service;

    public AzureBlobService(AzureOAuth2Service azureOAuth2Service) {
        this.azureOAuth2Service = azureOAuth2Service;
    }

    public byte[] downloadBlob(String blobName) {
        String accessToken = azureOAuth2Service.getAccessToken();
        if (accessToken == null) {
            throw new RuntimeException("Failed to get access token");
        }

        String blobUrl = String.format("https://%s.blob.core.windows.net/%s/%s", accountName, containerName, blobName);

        BlobClientBuilder blobClientBuilder = new BlobClientBuilder()
                .endpoint(blobUrl)
                .sasToken(accessToken);  // Pass the OAuth2 access token as a SAS token

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        blobClientBuilder.buildClient().download(outputStream);

        return outputStream.toByteArray();
    }
}
