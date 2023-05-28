# Spring java microservices
#### RESTful Api applications, that provides microservices for auth and restaurant control
###  Жуков Фёдор Сергеевич БПИ218
---

## Архитектура:
Данное приложение состоит из двух микросервисов на Java Spring и встроенной базы данных.
Первый микросервис представляет собой API, обеспечивающий функционал регистрации, авторизации и получения информации о пользователе, возможность для менеджера менять роли другим пользователям.
Второй микросервис представляет собой API, зарегестрированному пользователю просматривать меню, делать заказ. Менеджер может манипулировать блюдами, находящимися в ресторане.
#### Используемая база данных: H2 Database

## Спецификация API:
#### * = Manager only
#### Первый микросервис:
POST /api/auth/register - Регистрация

POST /api/auth/login - Авторизация

*Все последующие запросы доступны как минимум авторизированных с помощью JWT Токена пользователям!*
GET /api/info - Информация о пользователе по токену

PUT /api/manager/changeRole* - Смена роли пользователя   

#### Второй микросервис:
GET /api/order/menu - Получение меню

POST /api/order/new - Создание заказа

GET /api/order/{id} - Получение информации о заказе по его идентификатору

GET /api/dish/getAll* - Получение списка всех блюд в ресторане (доступных и недоступных)

POST /api/dish/add* - Добавление нового блюда

DEL /api/dish/delete* - Удаление блюда

PUT /api/dish/update* - Обновление кол-ва блюда в ресторане

*Последние четыре метода реализуют CRUD для блюд ресторана*
### [Postman Collection](https://www.postman.com/fedosz/workspace/auth-and-restaurant-api/overview)

