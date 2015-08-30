package com.tcmonitor.client.service.sender;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcmonitor.client.service.LogsPopulation;
import com.tcmonitor.model.Health;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class S3MessageSender implements MessageSender {

    private static final String FILE_NAME = "tcmonitor";
    private static final String HEALTH_NAME = "healthtcmonitor";

    @Value("${tcmonitor.aws.key}")
    private String accessKeyId;

    @Value("${tcmonitor.aws.secret}")
    private String secretKey;

    @Value("${tcmonitor.s3.bucket}")
    private String bucket;

    @Autowired
    private LogsPopulation logsPopulation;

    private AmazonS3Client s3Client;

    @PostConstruct
    public S3MessageSender init() {
        s3Client = new AmazonS3Client(new BasicAWSCredentials(accessKeyId, secretKey));
        return this;
    }

    @Override
    public void send(String message, String fileName) {
        try {
            saveMapper(logsPopulation.populate(message, fileName), FILE_NAME + System.currentTimeMillis());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void healthSend(Health health) {
        try {
            saveMapper(health, HEALTH_NAME + System.currentTimeMillis());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void saveMapper(Object object, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        byte[] content = mapper.writeValueAsBytes(object);
        ByteArrayInputStream bis = new ByteArrayInputStream(content);
        ObjectMetadata omd = new ObjectMetadata();
        omd.setContentLength(content.length);
        try {
            s3Client.putObject(bucket, fileName, bis, omd);
        } catch (AmazonClientException ae) {
            throw new RuntimeException(ae.getMessage());
        }
    }

}
