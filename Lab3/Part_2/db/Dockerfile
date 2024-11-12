FROM postgres:14

ENV POSTGRES_DB=mydatabase
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=password

ENTRYPOINT ["sh", "-c", "psql -U postgres -c \"CREATE USER $POSTGRES_USER WITH PASSWORD '$POSTGRES_PASSWORD';\" && psql -U postgres -c \"CREATE DATABASE $POSTGRES_DB;\" && exec postgres"]
