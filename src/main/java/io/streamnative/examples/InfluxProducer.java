// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package io.streamnative.examples;

import com.beust.jcommander.JCommander;
import com.google.common.collect.Maps;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
/**
 * IoT Producer
 */
public class InfluxProducer {

    private static String DEFAULT_TOPIC = "persistent://public/default/iotjetsonjson2";

    /**
     * GB
     * @return String   disk space
     */
    private static String getDiskSpace() {
        File cDrive = new File("/");
        return String.format("%.2f",
                (double)cDrive.getTotalSpace() /1073741824);
    }
    /**
     * schemas https://pulsar.apache.org/docs/en/schema-understand/
     * http://pulsar.apache.org/docs/en/concepts-schema-registry/
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        Logger logger = LoggerFactory.getLogger(InfluxProducer.class);

        JCommanderPulsar jct = new JCommanderPulsar();
        JCommander jCommander = new JCommander(jct, args);
        if (jct.help) {
            jCommander.usage();
            return;
        }
        if (jct.topic == null || jct.topic.trim().length() <= 0) {
            jct.topic = "persistent://public/default/iotjetsonjson2";
        }
        if (jct.serviceUrl == null || jct.serviceUrl.trim().length() <=0 ) {
            jct.serviceUrl = "pulsar://pulsar1:6650";
        }
        System.out.println("topic:" + jct.topic);
        System.out.println("url:" + jct.serviceUrl);

        PulsarClient client = null;

            try {
                client = PulsarClient.builder().serviceUrl(jct.serviceUrl.toString()).build();
            } catch (PulsarClientException e) {
                e.printStackTrace();
            }

        UUID uuidKey = UUID.randomUUID();
        String pulsarKey = uuidKey.toString();
        String OS = System.getProperty("os.name").toLowerCase();

        Cpu cpu = new Cpu();
        cpu.setMeasurement("diskspace");
        long timestamp = Instant.now().toEpochMilli();
        cpu.timestamp = timestamp;
        cpu.tags = Maps.newHashMap();
        cpu.tags.put("host", "pulsar1");
        cpu.tags.put("topic", jct.topic);
        cpu.fields = Maps.newHashMap();
        cpu.fields.put("drive", "/");
        cpu.fields.put("value", getDiskSpace());

        String topic = DEFAULT_TOPIC;
        if ( jct.topic != null && jct.topic.trim().length()>0) {
            topic = jct.topic.trim();
        }
        ProducerBuilder<Cpu> producerBuilder = client.newProducer(JSONSchema.of(Cpu.class))
                .topic(topic)
                .producerName("jetson-java").
                sendTimeout(5, TimeUnit.SECONDS);

        Producer<Cpu> producer = producerBuilder.create();

        MessageId msgID = producer.newMessage()
                .key(pulsarKey)
                .value(cpu)
                .send();

        System.out.println("Publish message ID " + msgID + " OS:" + OS + " Key:" + pulsarKey);

        producer.close();
        client.close();
        producer = null;
        client = null;
    }
}
