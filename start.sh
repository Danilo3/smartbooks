#!/usr/bin/env bash

mvn clean install
(java -jar user-service/target/user-service-1.0.jar&)
(java -jar api-gateway/target/api-gateway-1.0.jar&)