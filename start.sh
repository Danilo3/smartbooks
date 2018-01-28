#!/usr/bin/env bash

mvn clean install
(java -jar user-service/target/user-service-1.0-SNAPSHOT.jar&)
(java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar&)