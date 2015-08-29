package com.tcmonitor.client.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class SpringMvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

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
//            activeProfile = "prod,dbprod";
//        }
//        context.setInitParameter("spring.profiles.active", activeProfile);
    }

}
