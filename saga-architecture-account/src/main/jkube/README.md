```shell
mvn clean k8s:build k8s:push k8s:resource k8s:apply -Plkp  -DskipTests -Ddocker.registry.user=%DOCKER_REGISTRY_USER% -Ddocker.registry.password=%DOCKER_REGISTRY_PASSWORD%
```
```shell
kubect get all -n saga-
```