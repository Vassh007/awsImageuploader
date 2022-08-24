package com.Suvash.awsimageupload.profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Suvash.awsimageupload.bucket.BucketName;
import com.Suvash.awsimageupload.filestore.FileStore;
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.kafka.model.S3;

import static org.apache.http.entity.ContentType.*;


@Service
public class UserProfileService {

	private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }
	
    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
    	//1. check if image is not empty
    	isFileEmpty(file);
    	
    	//2. If File is an image
    	isImage(file);
    	
    	//3.The user exists in our database
    	UserProfile user = getUserProfileOrThrow(userProfileId);
    
    	//4.Grab some metadata from file if any
    	Map<String, String> metadata = extractMetadata(file);
    	
    	
    	//5.Store the image in s3  and update database(userprofileimagelink) with s3 image link
    	String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
    	String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
    	try {
			fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
			user.setUserProfileImageLink(fileName); 
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
    
    }

	private Map<String, String> extractMetadata(MultipartFile file) {
		Map<String, String> metadata = new HashMap<>();
    	metadata.put("Content-Type", file.getContentType());
    	metadata.put("Content-Length", String.valueOf(file.getSize()));
		return metadata;
	}


	private void isImage(MultipartFile file) {
		if (!Arrays.asList(
				IMAGE_JPEG.getMimeType(), 
				IMAGE_PNG.getMimeType(), 
				IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
    		throw new IllegalStateException("File must be an image[" + file.getContentType() + "]");
    	}
	}

  public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);

        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getUserProfileId());

        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);

    }


    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }

//    public byte[] download(String path, String key) {
//    	try {
//    		s3.getObject();
//    	} catch(AmazonServiceException e) {
//    		throw new IllegalStateException("Failed to download file to s3", e);
//    	}
//    }
//    
    
    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
    }

}


