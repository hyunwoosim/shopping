package com.hw.shopping.service;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Presigner s3Presigner;

    public String createPresignedUrl(String path) {
        var putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket) // 올릴 버킷명
            .key(path)      // 경로
            .build();
        var preSignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(3)) // URL 유효 기간
            .putObjectRequest(putObjectRequest)
            .build();
        return s3Presigner.presignPutObject(preSignRequest).url().toString();
    }

}