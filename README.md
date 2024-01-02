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
3. istio云原生
4. JWT与RSA验签
5. CI/CD
## 涉及核心表说明
表结构ddl定义在微服务resources下
## 使用步骤

## RSA JWT生成及使用

## CI/CD 设置

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