package com.hp.config.securitytag;

import freemarker.ext.jsp.TaglibFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class HpSecurityTldConfig {
    @Autowired
    private FreeMarkerConfigurer configurer;

    @PostConstruct //作用：是在实体类被初始化的时候，就会执行该注解标注的方法
    public void freeMarkerConfig() {
        List<String> tlds = new ArrayList<>();
        tlds.add("/tags/security.tld"); //设置自定义标签库
        TaglibFactory taglibFactory = configurer.getTaglibFactory();
        taglibFactory.setClasspathTlds(tlds); //应用我们自己的标签库
        if (taglibFactory.getObjectWrapper() == null) {
            taglibFactory.setObjectWrapper(configurer.getConfiguration().getObjectWrapper());
        }
    }
}
