#!/bin/bash
set -eo pipefail

# 获取容器IP
CLUSTER_IP=${POD_IP:-$(hostname -i)}

# 自动发现节点
if [[ "$1" == "redis-server" ]]; then
    shift

    # 等待集群节点就绪
    if [[ "$AUTO_CLUSTER" == "true" ]]; then
        echo "等待集群节点准备就绪..."
        sleep 10

        # 仅主节点执行集群创建
        if [[ "$ROLE" == "master" ]]; then
            nodes=""
            for i in $(seq 1 6); do
                nodes="$nodes redis-$i:6379"
            done

            echo "正在创建Redis集群..."
            echo "yes" | redis-cli --cluster create $nodes \
                --cluster-replicas 1 \
                -a ${REDIS_PASSWORD}
        fi
    fi

    # 启动Redis
    exec redis-server "$@"
fi

exec "$@"