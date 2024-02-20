package kmd.backend.magazine.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandler implements WebMvcConfigurer{
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        Path fileUploadPath =Paths.get("./assets/");
        String fileUploadDirectory = fileUploadPath.toFile().getAbsolutePath();

        registry.addResourceHandler("/assets/**").addResourceLocations("file:/"+ fileUploadDirectory + "/");
    }
}
