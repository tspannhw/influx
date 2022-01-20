package io.streamnative.examples;

import java.util.Map;
import java.util.StringJoiner;

/**
 */

public class Cpu  {

    public String measurement;
    public long timestamp;
    public Map<String, String> tags;
    public Map<String, Object> fields;

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Cpu() {
        super();
    }

    public Cpu(String measurement, long timestamp, Map<String, String> tags, Map<String, Object> fields) {
        super();
        this.measurement = measurement;
        this.timestamp = timestamp;
        this.tags = tags;
        this.fields = fields;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cpu.class.getSimpleName() + "[", "]")
                .add("measurement='" + measurement + "'")
                .add("timestamp=" + timestamp)
                .add("tags=" + tags)
                .add("fields=" + fields)
                .toString();
    }
}
