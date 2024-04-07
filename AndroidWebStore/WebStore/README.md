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
docker ps -a
docker run -d --restart=always --name spu111_container -p 5453:80 novakvova/spu111-api


docker pull novakvova/spu111-api:latest
docker images --all
docker ps -a
docker stop spu111_container
docker rm spu111_container
docker run -d --restart=always --name spu111_container -p 5453:80 novakvova/spu111-api
```

```nginx options /etc/nginx/sites-available/default
server {
    server_name   spu111.itstep.click *.spu111.itstep.click;
    location / {
       proxy_pass         http://localhost:5453;
       proxy_http_version 1.1;
       proxy_set_header   Upgrade $http_upgrade;
       proxy_set_header   Connection keep-alive;
       proxy_set_header   Host $host;
       proxy_cache_bypass $http_upgrade;
       proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
       proxy_set_header   X-Forwarded-Proto $scheme;
    }
}

sudo systemctl restart nginx
certbot
```



