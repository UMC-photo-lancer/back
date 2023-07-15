package shop.photolancer.photolancer.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("shop.photolancer.photolancer.web.controller")
    private String basePackage;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.ant("/**"))
                .build()
//                .host(this.externalHost)
                .apiInfo(this.apiInfo())
                .useDefaultResponseMessages(false);
    }

    /**
     * API Documents Information
     * @return
     */
    private ApiInfo apiInfo() {
        String title = "PhotoLancer API Documents"; // 스웨거 UI 타이틀
        String version = "1.0.0";
        String license = "photo_lancer All rights reserved";

        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .license(license)
                .build();
    }
}