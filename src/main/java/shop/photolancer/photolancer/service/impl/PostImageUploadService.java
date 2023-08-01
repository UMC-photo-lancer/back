package shop.photolancer.photolancer.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.photolancer.photolancer.domain.mapping.PostImage;
import shop.photolancer.photolancer.repository.PostImgRepository;
import shop.photolancer.photolancer.service.S3UploadService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostImageUploadService implements S3UploadService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final PostImgRepository postImgRepository;
    @Override
    public List<String> upload(List<MultipartFile> multipartFile) {
        List<String> imgUrlList = new ArrayList<>();

        for (MultipartFile file : multipartFile) {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket+"/post/image", fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgUrlList.add(amazonS3.getUrl(bucket+"/post/image", fileName).toString());
            } catch(IOException e) {

            }
        }
        return imgUrlList;
    }

    @Override
    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // file 삭제
    public void deleteFile(Long postId) {
        try {
                List<PostImage> postImages  = postImgRepository.findByPostId(postId);
                List<String> postImageUriList = postImages.stream().map(PostImage::getUri).collect(Collectors.toList());

                for (String p : postImageUriList) {
                    String fileName = p.substring(p.lastIndexOf('/' ) + 1);
                    amazonS3.deleteObject(bucket+"/post/image", fileName);
                }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName.length() == 0) {

        }
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {

        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

}
