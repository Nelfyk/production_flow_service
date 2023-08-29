# production_flow_service.
### Небольшой сервис для управления техническими картами на предприятии, общение с которым реализовано по gRPC методам(протокол HTTP/2). 
### Основные данынх хранится в postgresql, а изображения и координаты в mongodb(в gridFS и обычной коллекции).

методы:
  rpc getAllCommodityItems(google.protobuf.Empty) returns (stream CommodityItemResponse);
  rpc getAllWorkShopMap(google.protobuf.Empty) returns (stream WorkShopMapResponse);
  rpc createTechnoMap(CreateTechnoMapRequest) returns(CreateTechnoMapResponse);
  rpc getTechnoMap(TechnoMapRequest) returns (TechnoMapResponse);

Стек:
- Spring Boot
- Spring gRPC-starter
- Spring Data
- Maven
- БД PostgreSQL и MongoDB
- Docker
