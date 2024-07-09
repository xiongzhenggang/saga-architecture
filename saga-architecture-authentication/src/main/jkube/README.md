> k8s deploy
```shell
mvn clean package k8s:build k8s:push k8s:resource k8s:apply -Plkp  -DskipTests -Ddocker.registry.user=%DOCKER_REGISTRY_USER% -Ddocker.registry.password=%DOCKER_REGISTRY_PASSWORD%
```
> k8s undeploy
```shell
mvn k8s:undeploy
```
```shell
kubect get all -n default
kubect get ingress 
```  
