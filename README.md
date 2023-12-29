# saga-architecture

## 使用说明

## 关键技术

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