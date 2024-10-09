package com.travelport.config;

import javax.sql.DataSource;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

  @Value("${app.db.username}")
  private String username;

  @Value("${app.db.password}")
  private String password;

  @Value("${app.db.driverClassName}")
  private String driverClassName;

  @Value("${app.db.url}")
  private String url;

  @Bean
  public DataSource dataSource() {
    var ds = new BasicDataSource();
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setDriverClassName(driverClassName);
    ds.setUrl(url);
    return ds;
  }

  @Bean
  public LocalSessionFactoryBean hibernateSessionFactory() {
    var sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.travelport.entities");
    var prop = new Properties();
    prop.put("hibernate.show_sql", "true");
    sessionFactory.setHibernateProperties(prop);

    // xml mapping - in the past xml files were used to map entities
    // sessionFactory.setMappingResources("com/travelport/entities/User.hbm.xml");

    // annotated mapping - in case you want to add more entities
    //sessionFactory.setAnnotatedClasses(User.class);
    return sessionFactory;
  }

}
