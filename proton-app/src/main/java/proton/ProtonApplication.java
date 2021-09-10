package proton;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import util.ProtonProperties;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableConfigurationProperties( ProtonProperties.class )

@SpringBootApplication
@EnableJpaAuditing
public class ProtonApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ProtonApplication.class);
    }

    @Bean
    @Primary
    @ConfigurationProperties("sumo.datasource.mssql")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("sumo.datasource.mssql.configuration")
    public HikariDataSource firstDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}
