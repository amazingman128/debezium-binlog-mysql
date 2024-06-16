package com.nameless.binlog.debeziumbinlog.config;

import io.debezium.connector.mysql.MySqlConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;

@Configuration
public class DebeziumConfig {


    @Bean
    public io.debezium.config.Configuration customerConnector(Environment env) throws IOException {
        return io.debezium.config.Configuration.create()
                                               .with("name", "customer_mysql_connector")
                                               .with("connector.class", MySqlConnector.class.getName())
                                               .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                                               .with("offset.storage.file.filename", "./offset.dat")
                                               .with("offset.flush.interval.ms", "60000")
                                               //  历史变更记录
                                               .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                                               //历史变更记录存储位置，存储DDL
                                               .with("database.history.file.filename", "./history.dat")
                                               // 是否包含数据库表结构层面的变更，建议使用默认值true
                                               .with("include.schema.changes", "false")
                                               .with("topic.prefix", "my-app-connector")
                                               .with("schema.history.internal",
                                                       "io.debezium.storage.file.history.FileSchemaHistory"
                                               )
                                               .with("schema.history.internal.file.filename",
                                                       "./schema_history.dat"
                                               )
                                               .with("database.hostname", env.getProperty("customer.datasource.host"))
                                               .with("database.port", env.getProperty("customer.datasource.port")) // defaults to 5432
                                               .with("database.user", env.getProperty("customer.datasource.username"))
                                               .with("database.password", env.getProperty("customer.datasource.password"))
                                               .with("database.dbname", env.getProperty("customer.datasource.database"))
                                               .with("database.server.name", "customer-mysql-db-server")
                                               .with("database.server.id", "1")
                                               // 包含的数据库列表
                                               //                                               .with("database.include.list", "nameless_cloud_0")
                                               //                                               .with("table.include.list", "mch_info")
                                               .build();
    }
}
