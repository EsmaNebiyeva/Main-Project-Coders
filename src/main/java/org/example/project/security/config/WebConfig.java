//package org.example.project.security.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@Configuration
//@EnableWebMvc
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // Hangi yolların CORS'a izin vereceğini buradan belirliyoruz
//        registry.addMapping("/**")  // Tüm yollar için geçerli
//                .allowedOrigins("http://localhost:8080", "https://yourfrontend.com")  // İzin verilen kaynaklar
//                .allowedMethods(RequestMethod.GET.name(), RequestMethod.POST.name(), RequestMethod.PUT.name(), RequestMethod.DELETE.name())  // İzin verilen HTTP metodları
//                .allowedHeaders("Authorization", "Content-Type")  // İzin verilen başlıklar
//                .allowCredentials(true)  // Kimlik doğrulama bilgilerini kabul et
//                .maxAge(3600);  // Pre-flight isteklerinin geçerlilik süresi
//    }
//}
