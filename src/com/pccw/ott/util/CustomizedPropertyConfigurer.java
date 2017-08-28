package com.pccw.ott.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.web.context.ServletContextAware;

public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer implements ServletContextAware {

	private static Map<String, String> contextPropertiesMap;
	
	private static String webRootRealPath;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		contextPropertiesMap = new HashMap<String, String>();
		Iterator<Entry<Object, Object>> iterator = props.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			contextPropertiesMap.put(key, value);
		}
	}

	/**
	 * Get spring's context property by given key
	 * @param key property key value
	 * @return the value of the given key, if no corresponding key found, return null
	 */
	public static String getContextProperty(String key) {
		String value = null;
		if (contextPropertiesMap != null) {
			value = contextPropertiesMap.get(key);
		}
		return value;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		webRootRealPath = servletContext.getRealPath("/");
	}
	
	/**
	 * Get WebRoot's real path
	 * @return the absolute path of WebRoot
	 */
	public static String getWebRootRealPath() {
		return webRootRealPath;
	}

}
