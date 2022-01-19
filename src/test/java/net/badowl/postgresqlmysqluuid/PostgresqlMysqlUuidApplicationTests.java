package net.badowl.postgresqlmysqluuid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class PostgresqlMysqlUuidApplicationTests {
    private static final MySQLContainer mysqlContainer = ((MySQLContainer) new MySQLContainer("mysql:8")
            .withInitScript("data-mysql.sql"));
    private static final PostgreSQLContainer postgresqlContainer = ((PostgreSQLContainer) new PostgreSQLContainer("postgres:10.6")
            .withInitScript("data-postgresql.sql"))
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");
    ;

    static {
        mysqlContainer.start();
        System.setProperty("MYSQL_URL", mysqlContainer.getJdbcUrl());

        postgresqlContainer.start();
        System.setProperty("PG_URL", postgresqlContainer.getJdbcUrl());
    }

    @Autowired
    @Qualifier("jdbcTemplatePostgreSql")
    private NamedParameterJdbcTemplate jdbcTemplatePostgreSql;

    @Autowired
    @Qualifier("jdbcTemplateMySql")
    private NamedParameterJdbcTemplate jdbcTemplateMySql;

    @Test
    void postgreSqlTest() {
        test(jdbcTemplatePostgreSql);
    }

    @Test
    void mySqlTest() {
        test(jdbcTemplateMySql);
    }

    private void test(final NamedParameterJdbcTemplate jdbcTemplate) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final SqlParameterSource params = new MapSqlParameterSource("title", "Harry Poter");
        jdbcTemplate.update("INSERT INTO book (title) VALUES (:title)", params, keyHolder);
        assertFalse(keyHolder.getKeyList().isEmpty());
    }

}
