docker stop production_flow_service
docker rm production_flow_service
sudo docker build -t production_flow_service .
docker run -p 20003:8081 production_flow_service