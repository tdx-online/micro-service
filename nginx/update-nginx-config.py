import requests
import os

EUREKA_SERVER = "http://tdx-backend-eureka:8079/eureka"
NGINX_CONFIG_PATH = "/etc/nginx/conf.d/services.conf"

def fetch_services():
    response = requests.get(EUREKA_SERVER)
    services = response.json()
    return services

def update_nginx_config(services):
    with open(NGINX_CONFIG_PATH, 'w') as file:
        for service in services['applications']['application']:
            service_name = service['name'].lower()
            instances = service['instance']
            if service_name == 'user-service':  # 使用小写并替换下划线为连字符
                file.write(f"upstream user_service {{\n")
                for instance in instances:
                    ip = instance['ipAddr']
                    port = instance['port']['$']
                    file.write(f"    server {ip}:{port};\n")
                file.write("}\n")
            elif service_name == 'product-service':  # 使用小写并替换下划线为连字符
                file.write(f"upstream product_service {{\n")
                for instance in instances:
                    ip = instance['ipAddr']
                    port = instance['port']['$']
                    file.write(f"    server {ip}:{port};\n")
                file.write("}\n")

        # 写入server和location配置
        file.write("""
        server {
            listen 80;
        """)

        # user相关的location配置
        for path in ['/user/', '/cart/', '/order/']:
            file.write(f"""
            location {path} {{
                proxy_pass http://user_service{path};
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                
                add_header 'Access-Control-Allow-Origin' '*' always;
                add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS' always;
                add_header 'Access-Control-Allow-Headers' 'Authorization,Content-Type' always;

                if ($request_method = 'OPTIONS') {{
                    add_header 'Access-Control-Allow-Origin' '*' always;
                    add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS' always;
                    add_header 'Access-Control-Allow-Headers' 'Authorization,Content-Type' always;
                    add_header 'Access-Control-Max-Age' 86400;
                    return 204;
                }}
            }}
            """)

        # product相关的location配置
        for path in ['/category/', '/product/', '/property/', '/comment/', '/upload/']:
            file.write(f"""
            location {path} {{
                proxy_pass http://product_service{path};
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header X-Forwarded-Proto $scheme;
                
                add_header 'Access-Control-Allow-Origin' '*' always;
                add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS' always;
                add_header 'Access-Control-Allow-Headers' 'Authorization,Content-Type' always;

                if ($request_method = 'OPTIONS') {{
                    add_header 'Access-Control-Allow-Origin' '*' always;
                    add_header 'Access-Control-Allow-Methods' 'GET, POST, OPTIONS' always;
                    add_header 'Access-Control-Allow-Headers' 'Authorization,Content-Type' always;
                    add_header 'Access-Control-Max-Age' 86400;
                    return 204;
                }}
            }}
            """)

        # 写入nginx_status的location配置
        file.write("""
        location /nginx_status {
            stub_status on;
            allow all;
        }
        """)

        file.write("}\n")

def reload_nginx():
    os.system('nginx -s reload')

def main():
    services = fetch_services()
    update_nginx_config(services)
    reload_nginx()

if __name__ == "__main__":
    main()