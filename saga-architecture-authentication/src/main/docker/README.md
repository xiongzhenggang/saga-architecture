>docker run
```shell
docker run -p 8080:8080 -e JAVA_OPTS="-Dspring.profiles.active=dev -Dlogback.path=/app" image
```
> > jkube maven plugin
```shell
mvn clean package k8s:build -Plkp
```
> login docker registry then push
```shell
mvn clean package k8s:build k8s:push -Ddocker.registry.user=<user> -Ddocker.registry.password=<password>
```
