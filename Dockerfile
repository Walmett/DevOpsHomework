# Dockerfile
FROM alpine:3.11

USER root

RUN apk add --no-cache openssh-client ansible git sshpass python3 py3-pip docker-cli

RUN addgroup -S docker && adduser -S user -G docker

COPY ./ansible /ansible
COPY ./app /app

WORKDIR /ansible

# CMD ["ansible", "-i", "inventories/servers.yml", "-m", "ping", "all"]
CMD ["ansible-playbook", "playbook.yml", "-i", "inventories/servers.yml"]
