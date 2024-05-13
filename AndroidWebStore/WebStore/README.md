# Create react app in docker

Create docker hub repository - publish
```
docker build -t spu111-api . 
docker run -it --rm -p 5453:80 --name spu111_container spu111-api
docker run -d --restart=always --name spu111_container -p 5453:80 spu111-api
docker ps -a
docker stop spu111_container
docker rm spu111_container

docker images --all
docker rmi spu111-api

docker login
docker tag spu111-api:latest novakvova/spu111-api:latest
docker push novakvova/spu111-api:latest

docker pull novakvova/spu111-api:latest
docker run -d --restart=always --name spu111_container -p 5453:80 novakvova/spu111-api
```

