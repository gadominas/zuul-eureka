mvn -f eureka-server clean package -DskipTests
mvn -f api-gateway clean package -DskipTests
mvn -f foo-service clean package -DskipTests
mvn -f foo-consumer clean package -DskipTests

docker-compose build
docker-compose up
