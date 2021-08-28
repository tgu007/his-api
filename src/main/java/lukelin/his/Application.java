package lukelin.his;

import io.ebean.Ebean;
import lukelin.common.springboot.BaseSpringBootApplication;
import lukelin.his.domain.entity.basic.entity.Item;
import lukelin.his.system.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication(scanBasePackages = {"lukelin.common", "lukelin.his", "lukelin.sdk"})
public class Application extends BaseSpringBootApplication {


    public static void main(String[] args) {
//        String template="您提现{borrowAmount}元至尾号{tailNo}的请求失败，您可以重新提交提款申请。";
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("borrowAmount", "1000.00");
//        data.put("tailNo", "1234");
//        System.out.println(render(template,data));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true)
                        .maxAge(3600)
                        .allowedHeaders("*");
            }
        };
    }


}
