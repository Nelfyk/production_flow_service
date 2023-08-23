docker stop production_flow_service
docker rm production_flow_service
sudo docker build -f Dockerfile_java -t production_flow_service .
docker run -p 20003:8081 production_flow_service