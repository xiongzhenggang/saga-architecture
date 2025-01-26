from kubernetes import client, config, watch

# 加载 Kubernetes 配置
config.load_kube_config()

# Kubernetes 客户端初始化
v1 = client.CoreV1Api()
watcher = watch.Watch()

# 本地缓存实例
service_cache = LRUCache(capacity=100)
def watch_services_and_update_cache():
    print("开始监听 Kubernetes 服务更新...")
    for event in watcher.stream(v1.list_service_for_all_namespaces):
        service = event['object']
        event_type = event['type']

        service_name = f"{service.metadata.name}.{service.metadata.namespace}"
        service_data = {
            "cluster_ip": service.spec.cluster_ip,
            "ports": service.spec.ports
        }

        if event_type == "ADDED" or event_type == "MODIFIED":
            service_cache.put(service_name, service_data)
            print(f"缓存更新: {service_name} -> {service_data}")
        elif event_type == "DELETED":
            if service_name in service_cache.cache:
                service_cache.cache.pop(service_name)
                print(f"缓存删除: {service_name}")

# 启动监听
watch_services_and_update_cache()
