package com.ott.server.config;




//@Configuration
public class S3Config {
   // @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

   // @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

   // @Value("${cloud.aws.region.static}")
    private String region;

//    @Bean
//    public AmazonS3 amazonS3Client() {
////        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
////
////        return AmazonS3ClientBuilder
////                .standard()
////                .withCredentials(new AWSStaticCredentialsProvider(credentials))
////                .withRegion(region)
////                .build();
//        return null;
//    }

}