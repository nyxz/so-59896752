package net.badowl.postgresqlmysqluuid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class PostgresqlMysqlUuidApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostgresqlMysqlUuidApplication.class, args);
    }

    @Bean
    DataSource postgreSqlDs() {
        String pg_url = System.getProperty("PG_URL");
        return DataSourceBuilder
                .create()
                .username("test")
                .password("test")
                .url(pg_url)
                .build();
    }

    @Bean
    DataSource mySqlDs() {
        String mysql_url = System.getProperty("MYSQL_URL");
        return DataSourceBuilder
                .create()
                .username("test")
                .password("test")
                .url(mysql_url)
                .build();
    }

    @Bean
    NamedParameterJdbcTemplate jdbcTemplatePostgreSql() {
        return new NamedParameterJdbcTemplate(new JdbcTemplate(postgreSqlDs()));
    }

    @Bean
    NamedParameterJdbcTemplate jdbcTemplateMySql() {
        return new NamedParameterJdbcTemplate(new JdbcTemplate(mySqlDs()));
    }
}
