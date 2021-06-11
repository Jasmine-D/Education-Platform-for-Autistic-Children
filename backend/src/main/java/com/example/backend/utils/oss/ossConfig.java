package com.example.backend.utils.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//客户端配置类
@Configuration
public class ossConfig {
    /**
     * 定义ossClient bean对象
     */
    @Bean
    public OSS ossClient() {
        return new OSSClientBuilder().build(ossConstant.END_POINT, ossConstant.ACCESS_KEY_ID, ossConstant.ACCESS_KEY_SECRET);
    }
}
