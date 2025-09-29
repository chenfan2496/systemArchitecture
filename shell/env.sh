nohup /Volumes/SSD/tool/kafka_2.13-3.7.2/bin/kafka-server-start.sh /Volumes/SSD/tool/kafka_2.13-3.7.2/config/kraft/server.properties > /Volumes/SSD/tool/kafka_2.13-3.7.2/kafkaLogs/log 2>&1 &

docker run -d \
  --name skckill-container \
  -p 8080:8080 \
  -v /Volumes/SSD/projects/logs/systemArchitecture:/app/logs/systemArchitecture \
  skckill-app:latest

wrk -t4 -c100 -d10s --latency http://localhost:8080/seckill