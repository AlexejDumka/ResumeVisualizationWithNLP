FROM postgres:latest

ENV POSTGRES_DB resumeDB
ENV POSTGRES_USER pg
ENV POSTGRES_PASSWORD pg


COPY init-db.sh /docker-entrypoint-initdb.d/
COPY init-schema.sql /docker-entrypoint-initdb.d/

RUN chmod +x /docker-entrypoint-initdb.d/init-db.sh

# Port PostgreSQL
EXPOSE 5432
