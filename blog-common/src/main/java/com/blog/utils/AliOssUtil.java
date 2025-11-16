package com.blog.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;


@Slf4j
public class AliOssUtil {

    public static String upLoad(String endpoint,
                                String accessKeyId,
                                String accessKeySecret,
                                String BucketName,
                                byte[] file,
                                String fileName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            ossClient.putObject(BucketName,fileName,new ByteArrayInputStream(file));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        }finally {
            if(ossClient!=null){
                ossClient.shutdown();
            }
        }

        StringBuilder stringBuilder = new StringBuilder("https://")
                .append(BucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(fileName);
        log.info("AliOssUtil.java提示：文件上传成功，上传地址为{}", stringBuilder);

        //https://BucketName.endpoint/filename

        return stringBuilder.toString();
    }
    public static void delete(String endpoint,
                              String accessKeyId,
                              String accessKeySecret,
                              String BucketName,
                              String fileName){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    try{
        ossClient.deleteObject(BucketName,fileName);
    } catch (OSSException oe) {
        System.out.println("Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason.");
        System.out.println("Error Message:" + oe.getErrorMessage());
        System.out.println("Error Code:" + oe.getErrorCode());
        System.out.println("Request ID:" + oe.getRequestId());
        System.out.println("Host ID:" + oe.getHostId());
    } catch (ClientException ce) {
        System.out.println("Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network.");
        System.out.println("Error Message:" + ce.getMessage());
    }finally {
        if(ossClient!=null){
            ossClient.shutdown();
        }
    }

    }

    public static String download(String endpoint,
                                String accessKeyId,
                                String accessKeySecret,
                                String BucketName,
                                String fileName) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try{
            InputStream ossObject = ossClient.getObject(BucketName, fileName).getObjectContent();
            StringBuffer stringBuffer = new StringBuffer();
            new BufferedReader(new InputStreamReader(ossObject)).lines().forEach(
                    line -> {
                        stringBuffer.append(line).append("\n");
                    }
            );
            return stringBuffer.toString();

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if(ossClient!=null){
                ossClient.shutdown();
            }
        }
        return null;
    }
}
