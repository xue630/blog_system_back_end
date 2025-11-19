package com.blog;

import com.blog.properties.AliOssProperties;
import com.blog.utils.AliOssUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
public class AliOssTest {

    @Autowired
    AliOssProperties aliOssProperties;
    @Test
    public void testUpload() throws IOException {
        File file = new File("C:\\Users\\xue\\Desktop\\文件夹\\笔记资料\\Maven依赖管理项目构建工具.md");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fileInputStream.read(data);
        String url = AliOssUtil.upLoad(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName(),
                data,
                "test.md"
        );
        System.out.println(url);
    }
    @Test
    public void testDownload() throws IOException {
        String download = AliOssUtil.download(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName(),
                "article/博客系统开发文档.md"
        );
        System.out.println(download);
    }
    @Test
    public void testConfig(){
        System.out.println(aliOssProperties);
    }
    @Test
    public void testDelete() throws IOException {
        AliOssUtil.delete(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName(),
                "article/测试文件.md");
    }
}
