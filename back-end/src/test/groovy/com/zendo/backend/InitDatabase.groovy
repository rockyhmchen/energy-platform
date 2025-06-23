package com.zendo.backend

import groovy.sql.Sql
import org.apache.commons.lang3.StringUtils
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.shaded.org.apache.commons.io.IOUtils

import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager

class InitDatabase {

    static Connection connection
    static Sql sqlConnection

    PostgreSQLContainer dbContainer

    InitDatabase(dbContainer) {
        this.dbContainer = dbContainer
    }

    Sql createSqlConnection(dbName) {
        def host = dbContainer.getHost()
        def port = dbContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)
        def username = dbContainer.getUsername()
        def password = dbContainer.getPassword()

        def db = [
                url     : "jdbc:postgresql://$host:$port/$dbName",
                user    : username,
                password: password,
                driver  : "org.postgresql.Driver"
        ]

        this.sqlConnection = Sql.newInstance(db.url, db.user, db.password, db.driver)
        return sqlConnection;
    }

    Connection createConnection(dbName) {
        def host = dbContainer.getHost()
        def port = dbContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT)
        def username = dbContainer.getUsername()
        def password = dbContainer.getPassword()

        def db = [
                url     : "jdbc:postgresql://$host:$port/$dbName",
                user    : username,
                password: password,
                driver  : "org.postgresql.Driver"
        ]

        this.connection = DriverManager.getConnection(db.url, db.user, db.password)
        return connection;
    }

    def runScripts(scriptDir) {
        def fullDirResource = Thread.currentThread().getContextClassLoader().getResource(scriptDir)
        def fullDirPath = Paths.get(fullDirResource.toURI())
        def dir = new File(fullDirPath.toString())

        dir.listFiles()
                .sort({
                    StringUtils.isEmpty(StringUtils.substringBetween(it.name, 'V', '_')) ? 0 :
                            Integer.parseInt(StringUtils.substringBetween(it.name, 'V', '_'))
                })
                .each { file -> runScript(String.join('/', [scriptDir, file.name])) }
    }
    
    def runScript(scriptPath) {
        def resource = Thread.currentThread().getContextClassLoader().getResource(scriptPath)
        def scripts = IOUtils.toString(resource, StandardCharsets.UTF_8)

        runScript("zendo", scripts)
    }

    def runScript(dbName, scripts) {
        def conn = createSqlConnection(dbName)
        conn.execute(scripts)
        conn.close()
    }
}
