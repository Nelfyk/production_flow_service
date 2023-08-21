docker stop production_flow_service_postgresql
docker rm production_flow_service_postgresql
sudo docker build -f Dockerfile_postgres -t production_flow_service_postgresql .
docker run -d -p 20004:5432 -v '/etc/production_flow_service/database:/var/lib/postgresql/data' production_flow_service_postgresql

