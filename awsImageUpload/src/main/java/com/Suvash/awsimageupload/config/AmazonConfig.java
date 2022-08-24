package com.Suvash.awsimageupload.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {
	
	@Bean
	public AmazonS3 s3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(
				"AKIA4EVBE6QG53W3H7MV",
				"nGRE4s5HnosZI1O8MoQ1S8zAw16o4Py2EP8ULq2X"
				);
		return AmazonS3ClientBuilder
					.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
					.withRegion("us-east-1").build();
	}

}
