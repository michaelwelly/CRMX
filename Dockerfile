FROM maven:3.8.3-openjdk-17
# Устанавливаем рабочую директорию
WORKDIR /app
# Копируем файл jar в контейнер
COPY . /app/
# Открываем порт 7080
EXPOSE 7080
# Запускаем приложение
RUN mvn clean package
CMD ["mvn","spring-boot:run"]