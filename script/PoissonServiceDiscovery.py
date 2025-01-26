import numpy as np

class PoissonServiceDiscovery:
    def __init__(self, lambda_rate):
        self.lambda_rate = lambda_rate

#  根据实际情况得到解决，此处模拟
    def simulate_requests(self, duration):
        # 模拟 duration 时间内的服务请求
        requests = np.random.poisson(self.lambda_rate, duration)
        return requests

    def adjust_cache_refresh(self, request_rate):
        # 根据当前请求率动态调整缓存刷新
        if request_rate > self.lambda_rate:
            return "Increase refresh frequency"
        else:
            return "Maintain or reduce frequency"

# 示例
discovery = PoissonServiceDiscovery(lambda_rate=10)  # 平均每秒 10 次请求
requests = discovery.simulate_requests(duration=60)  # 模拟 60 秒的请求
print(f"请求速率：{requests}")
adjustment = discovery.adjust_cache_refresh(requests.mean())
print(f"请求速率：{requests.mean()}, 调整策略：{adjustment}")
