# Используем официальный образ Python как базовый
FROM python:3.11-slim

WORKDIR /app

COPY requirements.txt .

RUN python -m venv venv

RUN venv/bin/pip install --upgrade pip
RUN venv/bin/pip install -r requirements.txt

COPY . .

CMD ["venv/bin/python", "server.py"]
