package com.travelport.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    var appContext = new AnnotationConfigWebApplicationContext();
    appContext.scan("com.travelport");
    servletContext.addListener(new ContextLoaderListener(appContext));

    var servlet =
        servletContext.addServlet(
            "spring", new DispatcherServlet(new GenericWebApplicationContext()));
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/");
  }
}
