package com.storm.score.config;

import io.swagger.models.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    /*
    Docket: Swagger 설정의 핵심이 되는 Bean
    useDefaultResponseMessages: Swagger 에서 제공해주는 기본 응답 코드 (200, 401, 403, 404). false 로 설정하면 기본 응답 코드를 노출하지 않음
    apis: api 스펙이 작성되어 있는 패키지 (Controller) 를 지정
    paths: apis 에 있는 API 중 특정 path 를 선택
    apiInfo:Swagger UI 로 노출할 정보
    */

  private static final String SERVICE_NAME = "Make Project";
  private static final String API_VERSION = "V1";
  private static final String API_DESCRIPTION = "MakeProject API TEST";
  private static final String API_URL = "http://localhost:8080/";

  @Bean
  public Docket api() {
    List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
    responseMessages.add(new ResponseMessageBuilder().code(200).message("[SUCCESS] 200 OK.").build());
    responseMessages.add(new ResponseMessageBuilder().code(500).message("[FAIL] 500 SERVER ERROR!").responseModel(new ModelRef("Error")).build());
    responseMessages.add(new ResponseMessageBuilder().code(404).message("[FAIL] 404 PAGE NOT FOUND!").build());

    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.storm.score"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("STORM SCORE")
        .version("1.0.0")
        .description("스톰 악보 앱 API 화면")
        .build();

  }// API INFO

  // 아래 부분은 WebMvcConfigure 를 상속받아서 설정하는 Mehtod
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
    // -- Static resources
//    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }
}