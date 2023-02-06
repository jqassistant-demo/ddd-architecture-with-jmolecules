package org.jqassistant.demo.architecture.hexagonal.config.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class TemplateResolver {

    @Bean
    public ITemplateResolver uiTemplateResolver() {
        ClassLoaderTemplateResolver userTemplateResolver = new ClassLoaderTemplateResolver();
        userTemplateResolver.setPrefix("adapters/primary/ui/");
        userTemplateResolver.setSuffix(".html");
        userTemplateResolver.setTemplateMode(TemplateMode.HTML);
        userTemplateResolver.setCharacterEncoding("UTF-8");
        userTemplateResolver.setOrder(1);
        userTemplateResolver.setCheckExistence(true);
        return userTemplateResolver;
    }

}
