package com.in28minutes.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

// load đầu tiên khi chạy chương trình
@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "customersEntityManager",
        transactionManagerRef = "customersTransactionManager",
        basePackages = {"com.in28minutes.springboot.repository"}
)
// tao environment tu application.properties
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    // config dataSource kết nối đến database
    @Bean("customersDataSource")
    @Primary
    public DataSource getDataSourceMysql() {
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.url(environment.getProperty("spring.datasource.url"));
        dataSource.username(environment.getProperty("spring.datasource.username"));
        dataSource.password(environment.getProperty("spring.datasource.password"));
        return dataSource.build();
    }

    @Bean(name = "customersEntityManager")
    public LocalContainerEntityManagerFactoryBean getCustomersEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("customersDataSource") DataSource customersDataSource) {
        // config bean entity của data base tương đương với bảng, config jpa
        LocalContainerEntityManagerFactoryBean result = builder
                .dataSource(customersDataSource)
                .packages("com.in28minutes.springboot.model")
                .persistenceUnit("customer")
                .properties(additionalJpaProperties())
                .build();
        return result;
    }

    Map<String, String> additionalJpaProperties() {
        Map<String, String> properties = new HashMap<>();

        properties.put("spring.jpa.hibernate.ddl-auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.show_sql", environment.getProperty("spring.jpa.show-sql"));

        return properties;
    }

    @Bean(name = "customersTransactionManager")
    public JpaTransactionManager transactionManager(@Qualifier("customersEntityManager") EntityManagerFactory customersEntityManager) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(customersEntityManager);
        return transactionManager;
    }

}

