#!/bin/bash
set -e

# 基础连通性检查
if ! ping="$(redis-cli -a ${REDIS_PASSWORD} ping)" || [ "$ping" != 'PONG' ]; then
    exit 1
fi

# 集群状态检查
if [[ "$CLUSTER_ENABLED" == "yes" ]]; then
    if ! cluster_info="$(redis-cli -a ${REDIS_PASSWORD} cluster info)"; then
        exit 1
    fi
    if echo "$cluster_info" | grep -q 'cluster_state:fail'; then
        exit 1
    fi
fi

exit 0