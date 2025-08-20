FROM postgres:14.7-alpine
LABEL authors="AyselS"

COPY db/create-db-infrastructure.sql /docker-entrypoint-initdb.d/