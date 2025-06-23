package com.zendo.backend

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import com.github.tomakehurst.wiremock.WireMockServer
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class ComponentTestSpec extends Specification {

    @Autowired
    MockMvc webTester

    @Shared
    WireMockServer wireMockServer

    @Shared
    InitDatabase initDb

    @Shared
    DbUnitHelper dbUnitHelper

    Sql sql

    static int containerPort = 5432
    static int localPort = 57999

    @Shared
    static PostgreSQLContainer staticPostgreSQLContainer = new PostgreSQLContainer("postgres:10.17")
            .withDatabaseName("zendo")
            .withReuse(true)
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd ->
                    cmd.withHostConfig(
                            new HostConfig().withPortBindings(
                                    new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort))
                            )
                    ))

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driverClassName", () -> "org.postgresql.Driver");
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://" + staticPostgreSQLContainer.getHost() + ":" + staticPostgreSQLContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT) + "/zendo");
        registry.add("spring.datasource.username", () -> staticPostgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> staticPostgreSQLContainer.getPassword());
    }

    def setupSpec() {
        staticPostgreSQLContainer.start()
        staticPostgreSQLContainer.isRunning()

        initDb = new InitDatabase(staticPostgreSQLContainer)
        initDb.runScripts("db/migration")

        this.dbUnitHelper = new DbUnitHelper(initDb.createConnection("zendo"))

        this.wireMockServer = new WireMockServer(30030)
        wireMockServer.start()
    }

    def cleanupSpec() {
        wireMockServer.stop()
        staticPostgreSQLContainer.stop()
        !staticPostgreSQLContainer.isRunning()
    }

    def setup() {
        sql = initDb.createSqlConnection("zendo")
    }

    def cleanup() {
        sql.close()
    }
}
