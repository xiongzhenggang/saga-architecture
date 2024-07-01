> jkube maven plugin
```shell
mvn clean package k8s:build -Plkp
```
> login docker registry then push
```shell
mvn clean package k8s:build k8s:push -Ddocker.registry.user=<user> -Ddocker.registry.password=<password>
```