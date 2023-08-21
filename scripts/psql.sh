docker stop production_flow_service_postgresql
docker rm production_flow_service_postgresql
sudo docker build -f Dockerfile_postgres -t production_flow_service_postgresql .
docker run -p 20004:5432 production_flow_service_postgresql

