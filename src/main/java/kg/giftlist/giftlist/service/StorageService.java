package kg.giftlist.giftlist.service;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;
import lombok.*;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.Map;

@Service
@Getter
@Setter
@Log4j2
@RequiredArgsConstructor
public class StorageService {

    private final S3Client s3;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.bucketUrl}")
    private String bucketPath;

    public Map<String, String> upload(MultipartFile file) throws IOException {

        log.info("Uploading file ...");
        String key = System.currentTimeMillis() + file.getOriginalFilename();
        PutObjectRequest por = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(por, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        log.info("Upload complete.");
        return Map.of(
                "link", bucketPath + key
        );
    }

    public Map<String, String> delete(String fileLink) {

        log.info("Deleting file...");
        try {
            String key = fileLink.substring(bucketPath.length());
            log.warn("Deleting object: {}", key);
            s3.deleteObject(dor -> dor.bucket(bucketName).key(key).build());
        } catch (S3Exception e) {
            throw new IllegalStateException(e.awsErrorDetails().errorMessage());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
        return Map.of(
                "message", fileLink + " has been deleted."
        );

    }
}