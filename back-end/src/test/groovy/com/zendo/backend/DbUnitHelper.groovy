package com.zendo.backend

import org.dbunit.database.DatabaseConfig
import org.dbunit.database.DatabaseConnection
import org.dbunit.database.IDatabaseConnection
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.csv.CsvDataSet
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory
import org.dbunit.operation.DatabaseOperation
import org.springframework.util.ResourceUtils

import java.sql.Connection

class DbUnitHelper {

    String datasetDir
    IDatabaseConnection dbConn
    IDataSet dataset

    DbUnitHelper(Connection connection) {
        this.dbConn = new DatabaseConnection(connection)
        DatabaseConfig config = this.dbConn.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        config.setProperty(DatabaseConfig.FEATURE_ALLOW_EMPTY_FIELDS, true);
    }

    def setup(datasetDir) {
        this.dataset = new CsvDataSet(ResourceUtils.getFile("classpath:$datasetDir"));
        DatabaseOperation.DELETE_ALL.execute(this.dbConn, this.dataset);
        DatabaseOperation.INSERT.execute(this.dbConn, this.dataset);
    }

    def cleanup() {
        DatabaseOperation.DELETE_ALL.execute(this.dbConn, this.dataset);
        dbConn.close()
    }

}
