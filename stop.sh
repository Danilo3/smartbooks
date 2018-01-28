#!/usr/bin/env bash

prog_names=('user-service' 'api-gateway')
for prog in "${prog_names[@]}"; do
  ps ax | grep "$prog" | awk  ' NR < 2 {print $1}' | xargs  kill

done

