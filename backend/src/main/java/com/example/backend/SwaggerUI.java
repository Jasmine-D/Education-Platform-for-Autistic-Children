package com.example.backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//
@EnableSwagger2
//注解的作用支持Swagger 2的SpringFox支持
public class SwaggerUI {
    @Bean
    public Docket createSoftwareApi() {
        return new Docket(DocumentationType.SWAGGER_2)//告诉Docketbean正在使用Swagger规范的版本2。
                .apiInfo(apiInfo())//用来创建该Api的基本信息(这些基本信息会展现在文档页面中)
                .select()// 选创建构建器，用于定义有哪些控制器及其生成的文档中应包含哪些方法
                .apis(RequestHandlerSelectors.basePackage("com.example.backend"))//这里写的是API接口所在的包位置
                .apis(RequestHandlerSelectors.any())//用于定义要包含的类，这里包含所有类
                .paths(PathSelectors.any())//允许您根据路径映射定义应包含哪个控制器的方法，这里对所有路径进行监控
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("dym","http://www.bing.com/","1850052@tongji.edu.cn");
        ApiInfo build = new ApiInfoBuilder()
                .title("AiYu Study WebApp API")
                .description("By cdy dym")
                .contact(contact)
                .termsOfServiceUrl("http://www.baidu.com/")
                .version("1.0")//文档版本号
                .build();
        return build;
    }

}
