# saga-architecture
## 代码模块说明
1. saga-architecture-authentication: saga 用户鉴权服务，主要用于注册用户和登录时获取jwt
2. saga-architecture-order：订单saga流程，定义订单流程saga状态机模型，管理订购整体流程（包含saga编排器服务功能）
3. saga-architecture-account: 订单saga流程，用户信用卡账户服务，主要支付过程余额校验扣减
4. saga-architecture-goods： 订单saga流程，商品服务，主要用于购买商品检验商品库存以及补偿库存
5. saga-architecture-orchestrator： 
   1. saga-architecture-orchestrator-server：saga独立服务（暂未使用，本项目示例以Order订单服务为核心因此合并编排器服务，后续有多个Saga流程解耦可将订单服务中sagas包下服务移到此项目） 
   2. saga-architecture-orchestrator-kit：saga框架基本定义，命令事件组件等
6. saga-architecture-library-infrastructure：基础设施依赖包，主要管理依赖，rsa加密解密，统一异常请求等公共设施
7. saga-architecture-istio-manifests：为服务编写istio部署的yml脚本，以及rsa 公钥和私钥
8. saga-architecture-k8s-manifests: 各个服务发布到k8s的yml文件
## Saga编排定义
主服务Order中包含定义saga业务的整体流程
> saga dsl定义
```java
SagaDefinition<CreateOrderSagaData> sagaDefinition =
        step()
        .invokeLocal(this::create)
        //本地补偿
        .withCompensation(this::reject)
        .step()
        .invokeParticipant(this::reserveGoods)
        .onReply(GoodsStockLimit.class,this::handleGoodsLimit)
        .onReply(GoodsNotFound.class,this::handleGoodsNotFound)
        //异步远程补偿操作要定义
        .withCompensation(this::releaseGoods)
        .step()
        .invokeParticipant(this::reserveCredit)
        .onReply(CustomerNotFound.class, this::handleCustomerNotFound)
        .onReply(CustomerCreditLimitExceeded.class, this::handleCustomerCreditLimitExceeded)
        .step()
        //最终正向流程执行完成为成功
        .invokeLocal(this::approve)
        .build();
```
## 关键技术
1. 分布式事务saga编排模式
2. kafka事务消息
   1. Kafka 的事务基本上是配合其幂等机制来实现 Exactly Once 语义的
      1. 可替换rocketmq事务消息中间件
```json
kafka事务性消息实践
在这一节，我们将提供一些关于如何使用Kafka事务性消息的最佳实践。这包括如何确保消息的一次交付、监控和故障排查以及性能优化。
1 保障消息的一次交付
1.1 生产者幂等性
   确保生产者的幂等性是关键，以防止消息被重复发送。以下是一些关键策略和实践，用于确保生产者的幂等性：
   1. 分配唯一消息ID： 为每条消息分配一个唯一的消息ID。这可以是全局唯一的，也可以是特定于主题的唯一。在发送消息之前，生产者可以检查已经发送的消息记录，以确保当前消息的ID不重复。
   2. 使用幂等性API： Kafka 提供了幂等性的生产者 API。你可以在生产者配置中启用幂等性，设置 enable.idempotence=true，以确保消息在发送时不会被重复处理。
   3. 实现自定义幂等性： 在一些情况下，自定义实现幂等性逻辑可能是必要的。这可以涉及到在消息处理端的数据库或存储中跟踪已处理消息的状态，以确保消息不会被重复处理。
   4. 设置适当的重试机制： 如果消息发送失败，生产者应该具备适当的重试机制，以确保消息最终被成功发送。重试机制需要在生产者的配置中进行设置。
1.2 消费者去重
保障消息不会被重复处理同样至关重要。以下是一些策略和最佳实践，可用于实现消费者的去重：
   1. 幂等性消息处理逻辑： 消费者的消息处理逻辑应该是幂等的。这意味着无论消息被处理多少次，其结果都应该是相同的。这通常需要在应用程序代码中进行实施。
   2. 消息唯一标识： 为每条消息分配一个唯一的标识符，如消息ID。在处理消息前，消费者可以维护一个记录已处理消息的数据结构，以确保消息不会被重复处理。
   3. 消费者去重过程： 消费者在处理消息前，可以查询已处理消息的记录，如果消息已存在于记录中，可以选择跳过处理或进行进一步处理。这可以防止消息的重复处理。
   4. 消费者库支持： 一些消息队列处理库提供了内置的去重机制，你可以利用这些库来简化去重处理。
以上内容提供了详细的策略和最佳实践确保消息的一次交付。
1.3 事务性消息的性能考量
性能是任何消息系统的关键指标，特别是对于高吞吐量和低延迟的需求。以下是一些性能考量和优化策略：
1.3.1 性能调整
生产者性能调整：通过调整生产者的配置参数，如batch.size、acks等，可以优化消息发送性能。
消费者性能调整：消费者的性能也可以通过配置参数，如max.poll.records、fetch.min.bytes等进行调整。
```
3. istio云原生
4. JWT与RSA验签
5. CI/CD
## 涉及核心表说明
表结构ddl定义在微服务resources下
## 使用步骤

## RSA JWT生成及使用

## CI/CD 设置
>本地部署
> k8s deploy
```shell
mvn clean package k8s:build k8s:push k8s:resource k8s:apply -Plkp  -DskipTests -Ddocker.registry.user=%DOCKER_REGISTRY_USER% -Ddocker.registry.password=%DOCKER_REGISTRY_PASSWORD%
```
> k8s undeploy
```shell
mvn k8s:undeploy
```
> k8s 查看服务
```shell
kubect get all -n default
kubect get ingress 
```  

## 部署流程

## 测试流程
1. 开始下单流程（构造正常，异常订单执行查看补偿是否成功）
> POST: http://localhost:8080/orders
```json
{"customerId":"12223","goodsId":1,"goodsTotal":3,"orderTotal":{"amount":111}}
```
2. 查询订单情况
> GET: http://localhost:8080/orders

3. 查询账户信息
> GET:  http://localhost:8082/account/12223

4. 查询商品信息
> GET:  http://localhost:8081/goods/1