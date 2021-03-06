package com.sap.csc.dodorampup.config;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
/**
 * The packages Spring should scan to find the Repositories. Custom
 * implementations should have the postfix 'CustomImpl'
 */
@EnableJpaRepositories(repositoryImplementationPostfix = "CustomImpl", basePackages = {
		"com.sap.csc.dodorampup.repository" })
@EnableWebMvc
/**
 * The packages Spring should scan to find the beans. The order is important.
 */
@ComponentScan(basePackages = {//
		"com.sap.csc.dodorampup.repository",//
		"com.sap.csc.dodorampup.rest",//
		"com.sap.csc.dodorampup.controller",//
		"com.sap.csc.dodorampup.model",//
		"com.sap.csc.dodorampup.service"//
		})
/**
 * This let you use @Transactional
 */
@EnableTransactionManagement
/**
 * This let you use Pageable on Controllers
 */
@EnableSpringDataWebSupport
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class AppConfig extends WebMvcConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(AppConfig.class);


	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	/**
	 * Validator Bean for the Hibernate Validator
	 * 
	 * @return
	 */
	@Bean
	public Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	/**
	 * Function to trim empty spaces on all the String fields Only on empty
	 * spaces which are NOT in between the words
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
	}

}
