# Overview

Using Java to send data to Pulsar to InfluxDB v2.1.1 - InfluxCloud

# Prerequisites

- Java 1.8 or higher version
- Java Client: 2.9.1

## Details

## Get your InfluxDB Cloud Account

## Create a Bucket

pulsar

## Create A Token

https://us-Location.aws.cloud2.influxdata.com/notebook/from/bucket/pulsar


## Config conf/influxcloud.yml

````
configs:
    influxdbUrl: "https://us-east-1-1.aws.cloud2.influxdata.com"
    organization: "tim.spann@streamnative.io"
    bucket: "pulsar"
    token: "2THISISVERYLONGGENERATEinCLOUDog=="
    logLevel: "BASIC"
    precision: "ms"

````
