package ru.strebkov.Monitoring_Kafka.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MainMetrics {

    HTTP_SERVER_REQUESTS("http.server.requests"),
    HTTP_SERVER_REQUESTS_ACTIVE("http.server.requests.active"),
    HTTP_CLIENT_REQUESTS("http.client.requests"),
    HTTP_CLIENT_REQUESTS_ACTIVE("http.client.requests.active"),
    DISK_FREE("disk.free"),
    DISK_TOTAL("disk.total"),
    JVM_MEMORY_USED("jvm.memory.used"),
    JVM_MEMORY_MAX("jvm.memory.max"),
    JVM_MEMORY_COMMITTED("jvm.memory.committed"),
    JVM_GC_PAUSE("jvm.gc.pause"),
    JVM_INFO("jvm.info"),
    PROCESS_UPTIME("process.uptime");

    private final String address;

}
