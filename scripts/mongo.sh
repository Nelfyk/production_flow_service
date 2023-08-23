docker stop production_flow_service_mongo
docker rm production_flow_service_mongo
sudo docker build -f Dockerfile_mongo -t production_flow_service_mongo .
docker run -d -p 20005:27017 -v '/etc/production_flow_service/database/mongo:/var/lib/mongodb/' production_flow_service_postgresql