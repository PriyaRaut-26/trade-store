package com.trade.store.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;


@Configuration
public class H2DataSourceConfig {
	
	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String userName;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Bean(name="dataSource")
	public DataSource getDataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setUrl(this.url);
		dataSource.setDriverClassName(this.driverClassName);
		dataSource.setUsername(this.userName);
		dataSource.setPassword(this.password);
		return dataSource;	
		
	}
	
	@Bean(name="h2JdbcTemplate")
	public JdbcTemplate getH2JdbcTemplate()
	{
		return new JdbcTemplate(getDataSource());
		
	}	

}
