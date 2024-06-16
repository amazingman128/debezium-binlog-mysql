package com.nameless.binlog.debeziumbinlog.listener;

import com.nameless.binlog.debeziumbinlog.DebeziumUtils;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class DebeziumListener {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;

    public DebeziumListener(Configuration customerConnectorConfiguration) {
        debeziumEngine = DebeziumEngine.create(Json.class)
                                       .using(customerConnectorConfiguration.asProperties())
                                       .notifying(this::process).build();
    }


    public void process(ChangeEvent<String, String> event) {

        System.out.println("tableName" + DebeziumUtils.getTable(event));
        System.out.println(event.destination());
        System.out.println(event.headers());
        System.out.println(event.value());
    }


    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (Objects.nonNull(this.debeziumEngine)) {
            this.debeziumEngine.close();
        }
    }


}
