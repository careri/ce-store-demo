// package com.careri78;

// import javax.sql.DataSource;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.jdbc.datasource.DriverManagerDataSource;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.transaction.annotation.EnableTransactionManagement;

// import jakarta.persistence.EntityManagerFactory;

// @Configuration
// @EnableTransactionManagement
// public class InMemoryPersistanceConfiguration {

//     @Bean
//     public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//         LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//         em.setDataSource(dataSource());
//         em.setPackagesToScan(new String[] { "\\your package here" });

//         JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//         em.setJpaVendorAdapter(vendorAdapter);
//         em.setJpaProperties(additionalProperties());

//         return em;
//     }

//     @Bean
//     public DataSource dataSource() {
//         DriverManagerDataSource dataSource = new DriverManagerDataSource();
//         dataSource.setDriverClassName("\\Driver");
//         dataSource.setUrl("\\URL");
//         dataSource.setUsername("\\userName");
//         dataSource.setPassword("\\password");
//         return dataSource;
//     }

//     @Bean
//     public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//         JpaTransactionManager transactionManager = new JpaTransactionManager();
//         transactionManager.setEntityManagerFactory(emf);
//         return transactionManager;
//     }
// }