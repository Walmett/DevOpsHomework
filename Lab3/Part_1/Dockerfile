FROM python:3.10

WORKDIR /app

COPY html/index.html index.html

CMD ["python3", "-m", "http.server", "--bind", "0.0.0.0", "8080"]
