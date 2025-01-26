import threading
import time

def periodic_cache_refresh(interval: int):
    while True:
        print("开始同步服务注册表...")
        services = v1.list_service_for_all_namespaces().items
        for service in services:
            service_name = f"{service.metadata.name}.{service.metadata.namespace}"
            service_data = {
                "cluster_ip": service.spec.cluster_ip,
                "ports": service.spec.ports
            }
            service_cache.put(service_name, service_data)
        time.sleep(interval)

# 启动刷新任务
refresh_thread = threading.Thread(target=periodic_cache_refresh, args=(300,))
refresh_thread.daemon = True
refresh_thread.start()
