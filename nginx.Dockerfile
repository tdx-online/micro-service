FROM nginx:latest

# 安装cron和Python
RUN apt-get update && \
    apt-get install -y cron python3 python3-pip && \
    pip3 install requests

# 复制nginx配置文件
COPY nginx/nginx.conf /etc/nginx/nginx.conf

# 复制Python脚本
COPY nginx/update-nginx-config.py /usr/local/bin/update_nginx_config.py

# 设置cron作业，每5分钟运行一次脚本
# RUN echo "*/5 * * * * python3 /usr/local/bin/update_nginx_config.py" > /etc/cron.d/update_nginx

# 赋予正确的权限
# RUN chmod 0644 /etc/cron.d/update_nginx && crontab /etc/cron.d/update_nginx

# 启动cron和nginx
#CMD cron && nginx -g 'daemon off;'
CMD nginx -g 'daemon off;'