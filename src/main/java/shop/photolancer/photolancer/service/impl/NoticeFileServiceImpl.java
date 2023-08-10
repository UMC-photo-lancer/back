package shop.photolancer.photolancer.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.photolancer.photolancer.domain.Notice;
import shop.photolancer.photolancer.repository.NoticeFileRepository;
import shop.photolancer.photolancer.service.S3UploadService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoticeFileServiceImpl implements S3UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final NoticeFileRepository noticeFileRepository;

    @Override
    public List<String> uploadAWS(List<MultipartFile> multipartFile) {
        List<String> fileUrlList = new ArrayList<>();

        String fileName;
        for (MultipartFile file : multipartFile) {
            fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try (InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket + "/notice/file", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                fileUrlList.add(amazonS3.getUrl(bucket + "/notice/file", fileName).toString());
            } catch (IOException e) {

            }
        }
        return fileUrlList;
    }

        public String createFileName (String fileName){
            return UUID.randomUUID().toString().concat(fileName);
        }

        public void deleteFile(Notice notice) {
            System.out.println(notice.getId());
            noticeFileRepository.deleteAllByNotice(notice);
        }
}
