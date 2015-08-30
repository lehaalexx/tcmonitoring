package com.tcmonitor.server.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.tcmonitor.model.Logs;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3ContentChecker {

    private static final String FILE_NAME = "tcmonitor";

    @Value("${tcmonitor.aws.key}")
    private String accessKeyId;

    @Value("${tcmonitor.aws.secret}")
    private String secretKey;

    @Value("${tcmonitor.s3.bucket}")
    private String bucket;

    @Autowired
    private LogProcessor logProcessor;

    private AmazonS3Client s3Client;

    @PostConstruct
    public void init() {
        s3Client = new AmazonS3Client(new BasicAWSCredentials(accessKeyId, secretKey));
    }

    //Check logs every 5 seconds
    @Scheduled(cron = "*/5 * * * * *")
    public void check() {
        List<String> logs = getListForBucket();
        for(String log : logs){
            Logs s3Log = (Logs)get(Logs.class, log);
            logProcessor.processLogs(s3Log);
            delete(log);
        }

    }

    public List<String> getListForBucket() {
        List<String> list = new ArrayList<>();
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucket);
        listObjectsRequest.withPrefix(FILE_NAME);
        ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            String key = objectSummary.getKey();
            list.add(key);
        }
        return list;
    }

    public void delete(String fileName) {
        try {
            s3Client.deleteObject(bucket, fileName);
        } catch (AmazonClientException ae) {
            throw new RuntimeException(ae.getMessage());
        }
    }

    public Object get(Class<?> clazz, String fileName) {
        Object result;
        S3Object object;
        try {
            object = s3Client.getObject(bucket, fileName);
        } catch (AmazonClientException ae) {
            throw new RuntimeException(ae.getMessage());
        }
        InputStream input = object.getObjectContent();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        try {
            while ((n = input.read(buffer)) != -1) {
                output.write(buffer, 0, n);
            }
            byte[] content = output.toByteArray();
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(content, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
}
