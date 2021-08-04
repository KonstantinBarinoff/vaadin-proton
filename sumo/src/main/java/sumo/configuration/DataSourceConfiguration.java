package sumo.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class DataSourceConfiguration {
    
    @Bean
    @Primary
    @ConfigurationProperties("sumo.datasource.mssql")
    public DataSourceProperties msSqlDataSourceProperties() {
	return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("sumo.datasource.oracle")
    public DataSourceProperties oracleDataSourceProperties() {
	return new DataSourceProperties();
    }
    
    @Primary
    @ConfigurationProperties("sumo.datasource.mssql.hikari")
    public HikariDataSource msSqlDataSource() {
	log.debug("INITIALIZING MSSQL DATASOURCE");
	return msSqlDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @ConfigurationProperties("sumo.datasource.oracle.hikari")
    public HikariDataSource oracleDataSource() {
	log.debug("INITIALIZING ORACLE DATASOURCE");
	return oracleDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    @Bean(name = "jdbcSumo")
    public JdbcTemplate masterJdbcTemplate(HikariDataSource dsMaster) {
        return new JdbcTemplate(msSqlDataSource());
    }    

    @Bean(name = "jdbcOracle")
    public JdbcTemplate slaveJdbcTemplate(HikariDataSource dsSlave) {
        return new JdbcTemplate(oracleDataSource());
    }

    
    
}
