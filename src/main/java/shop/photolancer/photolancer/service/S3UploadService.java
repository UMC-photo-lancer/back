package shop.photolancer.photolancer.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3UploadService {
    List<String> uploadAWS(List<MultipartFile> multipartFile);
    String createFileName(String fileName);
}
