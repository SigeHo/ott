package com.pccw.ott.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackConfigListener implements ServletContextListener {  
    private static final Logger logger = LoggerFactory.getLogger(LogbackConfigListener.class);  
     
    public void contextInitialized(ServletContextEvent event) {  
        //Load logback.xml file
        String logbackConfigLocation = event.getServletContext().getInitParameter("logbackConfigLocation");  
        String path = event.getServletContext().getRealPath(logbackConfigLocation);  
        try {  
            LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();  
            loggerContext.reset();  
            JoranConfigurator joranConfigurator = new JoranConfigurator();  
            joranConfigurator.setContext(loggerContext);  
            joranConfigurator.doConfigure(path);  
            logger.debug("Loaded logback config file from: "+path);  
        }  
        catch (JoranException e) {  
            logger.error("Can't load logback config file from: " + path, e);  
        }  
    }  
    
    public void contextDestroyed(ServletContextEvent event) {      	
    }  
}