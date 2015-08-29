package com.tcmonitor.server.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SpringMvcInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext context) throws ServletException {
        super.onStartup(context);
        setSystemProperties(context);
    }
    
    protected void setSystemProperties(ServletContext context) {
//        String activeProfile = System.getProperty("spring.profiles.active");
//        if (activeProfile == null) {
//            activeProfile = "prod,swf";
//        }
//        context.setInitParameter("spring.profiles.active", activeProfile);
        System.setProperty("app-version", getAppVersion());
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        return new Filter[]{characterEncodingFilter};
    }

    private String getAppVersion() {
        return System.getProperty("user.name") + "-" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }
}
