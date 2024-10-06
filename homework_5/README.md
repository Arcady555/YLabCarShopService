# Сервис для управления автосалоном. Приложение позволяет пользователям управлять базой данных автомобилей, обрабатывать заказы клиентов на покупку и обслуживание автомобилей, а также управлять учетными записями пользователей.

## Welcome!

REST приложение. Отправка данных через HTTP-запросы

## Используемые технологии:

* Java 17

* Maven

* TomCat 9

* spring boot 3.2.0

* Liquibase

* Docker

### Запуск приложения с Maven
Перейдите в корень проекта через командную строку:
```
cd /home/......../IdeaProjects/YLabCarShopService
``` 
и выполните команду:
```
docker-compose up -d
```
Вы запустили контейнер для Базы данных. В той же директории выполните команду:
```
docker exec -it db_car_shop psql -U role_arcady postgres
```
после чего:
```
CREATE DATABASE y_lab_car_service;
exit
```

Перейдите в корень проекта(блок ParfenovCustomAspect) через командную строку: 

```
cd /home/......../IdeaProjects/YLabCarShopService/ParfenovCustomAspect
``` 
и выполните команду:
```
mvn clean install
```

Перейдите в корень проекта(блок homework_5) через командную строку:
```
cd /home/......../IdeaProjects/YLabCarShopService/homework_5
``` 
и выполните команды:
```
mvn clean install
mvn spring-boot:run
```

Отправляйте запросы, например через PostMan.
* Зарегистрируйтесь (http://localhost:8080/sign-up):
![image](images/1.png) Запомните ID в ответе сервера! Под ним и под паролем Вы будете заходить в систему.
* Теперь все запросы можно выполнять только заполнив данные во вкладке Autorization (в графу username вставляйте свой ID!) :
![image](images/9.png)

Как обычный клиент, вы можете:
* Посмотреть любую машину, введя её ID, (http://localhost:8080/car?id=) + ID:
![image](images/3.png)
* Обновить данные по своей машине, введя ID вашей машины и новые данные в форме JSON (http://localhost:8080//update-car):
![image](images/4.png) 
* Удалить свой заказ на машину (http://localhost:8080/delete-order?id=) + ID.
* Удалить свою машину из БД (http://localhost:8080/delete-car?id=) + ID.
* Создать заказ на машину(сервис - если машина Ваша, покупка - если машина ещё не Ваша) (http://localhost:8080/create-order):
* Создать карточку машины для записи в БД (http://localhost:8080/create-car):
  ![image](images/8.png)
* Есть поиск машины по интересующим Вас параметрам (http://localhost:8080/cars-with-parameters?ownerId=X&brand=X&model=X&yearOfProd=X&priceFrom=X&priceTo=X&condition=X) где вместо X впишите интересующие Вас параметры. Или пробел:
* Посмотреть список всех доступных машин (http://localhost:8080/all-cars):

Следующие запросы доступны только админу или менеджеру
* Посмотреть любой заказ (http://localhost:8080/order?id=) + ID:
![image](images/9.png)
* Найти заказы по параметрам (http://localhost:8080/orders-with-parameters?authorId=X&carId=X&type=X&status=X) где вместо X впишите интересующие Вас параметры. Или пробел:
![image](images/10.png)
* Закрыть любой заказ (http://localhost:8080/close-order?id=) + ID:
![image](images/11.png)
* Посмотреть все заказы (http://localhost:8080/all-orders):
![image](images/12.png)

Следующие запросы доступны только админу
* Посмотреть карточку любого пользователя (http://localhost:8080/user?id=) + ID:
  ![image](images/9.png)
* Найти группу пользователей по параметрам (http://localhost:8080/users-with-parameters?role=X?name=X&contactInfo=X&buysAmount=X) где вместо X впишите интересующие Вас параметры. Или пробел:
  ![image](images/10.png)
* Обновить карточку с данными пользователя (http://localhost:8080/update-user) :
  ![image](images/11.png)
* Посмотреть лог действий любого пользователя(выбрать по параметрам) (http://localhost:8080/log-with-parameters?userId=X?action=X&dateTimeFom=X&dateTimeTo=X) где вместо X впишите интересующие Вас параметры. Или пробел:
  ![image](images/12.png)
* Удалить карточку пользователя из БД (http://localhost:8080/delete-user?id=) + ID:
  ![image](images/12.png)
* Создать карточку пользователя (http://localhost:8080/create-user):
  ![image](images/12.png)
* Посмотреть список всех пользователей (http://localhost:8080/all-users):
  ![image](images/12.png)


## Have a good job!
