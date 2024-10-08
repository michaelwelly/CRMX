# Dispatcher DB Controller

### Предварительные требования

- Установленный JDK 17
- Установленный Maven
- Установленный Docker и Docker Compose

### 1. Сборка и запуск приложения 
 
#### Шаг 1: Соберите проект 
 
Сначала соберите ваш проект с помощью Maven:

mvn clean package

#### Шаг 2: Запустите Docker контейнер 
 
Теперь вы можете запустить контейнер с помощью Docker Compose:

docker-compose up --build

Теперь ваше приложение будет доступно по адресу  http://localhost:7080/api/healthcheck, и вы получите ответ "Сервис Dispatcher DB Controller". 
#### Шаг 3: Остановка приложения

Остановить приложение можно следующей командой: 

docker-compose down 

###
Для запуска с базой Postgresql установленной локально нужно в меню конфигурации запуска
Edit Configuration выставить Active Profile local
Для запуска с базой Postgresql установленной в докере нужно в меню конфигурации запуска
Edit Configuration выставить Active Profile docker