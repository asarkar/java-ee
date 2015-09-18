package com.nuance.dcs.userpref;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

@SpringBootApplication
public class UserPreferenceApp {
    public static void main(String[] args) throws Exception {
	SpringApplication.run(UserPreferenceApp.class, args);
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(
	    LocalContainerEntityManagerFactoryBean bean) {
	return bean.getObject();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(
	    DataSource dataSource) {

	LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();

	bean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
	bean.setDataSource(dataSource);
	EclipseLinkJpaVendorAdapter jva = new EclipseLinkJpaVendorAdapter();
	jva.setDatabase(Database.ORACLE);
	jva.setShowSql(true);
	bean.setJpaVendorAdapter(jva);

	return bean;
    }
}
