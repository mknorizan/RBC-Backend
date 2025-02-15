package com.rhumuda_new.rhumudasystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class ThymeleafConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafConfig.class);
    
    private final ResourceLoader resourceLoader;

    public ThymeleafConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        
        try {
            String templatePath = resourceLoader.getResource("classpath:/templates/").getFile().getAbsolutePath();
            logger.info("Template path: {}", templatePath);
            templateResolver.setPrefix(templatePath + "/");
        } catch (Exception e) {
            logger.error("Error setting template path", e);
        }
        
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(false);
        
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine springTemplateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
}
