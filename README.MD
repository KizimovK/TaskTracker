## Практическая работа №7 и №8

# Курса «Разработка на Spring Framework» от компании Skillbox. «Реализация приложение «Трекер задач»

## Цель практической работы

Закрепить знания, полученные по следующим темам:
<ol>
<li>Написание контроллеров с использованием Mono и Flux.</li>
<li>Работа с MongoDB в реактивной парадигме.</li>
<li>Настройка Basic-аутентификации в Spring WebFlux</li>
<li>Настройка прав пользователей на доступ к методам контроллеров</li>
</ol>

## Использование приложения. Документация RESTFull API

### Работа с пользователями

Получение (как списком, так и по ID), обновление и удаление профилей пользователей доступны
только тем клиентам, которые имеют одну из следующих ролей: ROLE_USER, ROLE_MANAGER.
Для авторизации пользователей используем Basic-аутентификацию, то есть указываем имя пользователя и  
пароль при выполнении запроса.

1. Добавление пользователя
    - POST-запрос

    ```http request
    http://localhost:8080/api/user/register
    ```
   Тело запроса:
    ```json body request
    {
        "username"  :"Anton",
        "email" : "anton@road.lp",
        "roles" : ["ROLE_MANAGER","ROLE_USER"],
        "password" : "123456"
    }
    ```

2. Показать всех пользователей
    - GET-запрос:
    ```http request
        http://localhost:8080/api/user
    ```

3. Авторизированного пользователя, его id берется из UserDetails
    - GET-запрос:
    ```http request
    http://localhost:8080/api/user/currentUser
    ```
4. Удаление пользователя, его id берется из UserDetails
    - DELETE-запрос:
    ```http request
    http://localhost:8080/api/user
    ```
5. Изменение пользователя, его id берется из UserDetails
    - PUT-запрос

    ```http request
    http://localhost:8080/api/user
    ```

    ```json
    {
     "email": "anton-somov@mail.ru",
      "password": "qwerty",
      "roles": [
         "ROLE_MANAGER"
          ]
    }
    ```

### Работа с заданиями

Получение списка задач, получение задачи по ID,
добавление наблюдателя доступны пользователю с одной из следующих ролей: ROLE_USER, ROLE_MANAGER;
Создание, обновление и удаление задачи доступны пользователю с ролью ROLE_MANAGER.

1. Найти все задачи (в ответе также должны находиться вложенные сущности,
   которые описывают автора задачи и исполнителя, а также содержат список наблюдающих за задачей):
    - GET-запрос:
        ```http request
            http://localhost:8080/api/task
        ```
2. Найти конкретную задачу по ID (в ответе также должны находиться вложенные сущности,
   которые описывают автора задачи и исполнителя, а также содержат список наблюдающих за задачей);
    - GET-запрос:
      ```http request
      http://localhost:8080/api/task/{taskId}
      ```
      Example Response:
      ```http response
        {
            "id": "67535a5306502c150cb6758d",
            "name": "Task Java Streams Reactive262",
            "description": "Java Spring Reactive and Security Application Example, Skillbox",
            "createdAt": "2024-12-06T20:10:59.547Z",
            "updatedAt": "2024-12-06T20:11:59.415167280Z",
            "status": "TODO",
            "author": {
                "id": "67533c8e761c965e2a83fb8e",
                "username": "Anton",
                "email": "anton-somov@mail.ru",
                "roles": [
                   "ROLE_MANAGER"
                    ]
             },
          "assignee": {
                "id": "67534a0153a22e0504dc77ed",
                "username": "Pavel",
                "email": "pavel@road.lp",
                "roles": [
                   "ROLE_USER"
                    ]
             },
        "observers": [
         {
            "id": "6753344eef173d1b745fc203",
            "username": "Leon",
            "email": "leon@mail.org",
            "roles": [
            "ROLE_USER"
                ]
            }
        ]    
        }
      ```
3. Создать задачу (может только пользователь с ролью ROLE_MANAGER), id автора берется из UserDetails:

- POST-запрос

    ```http request
    http://localhost:8080/api/task
    ```
  Тело запроса:
    ```json body request
    {
       "name" : "Task Java Streams Reactive262",
       "description" : "Java Spring Reactive and Security Application Example, Skillbox",
       "assigneeId" : "67534a0153a22e0504dc77ed"
    }
    ```

4. Обновить задачу (может только пользователь с ролью ROLE_MANAGER), id автора берется из UserDetails::

- PUT-запрос
    ```http request
    http://localhost:8080/api/task/{id}
    ```
  Request body, example
    ```json
    {
       "name" : "Task Java Streams Reactive262",
       "description" : "Java Spring Reactive Application Example, Skillbox",
       "authorId" : "67191cd9c1a39d2e3e0fc6ef",
       "assigneeId" : "67191cd2c1a39d2e3e0fc6ed"
    }
    ```

5. Добавиться наблюдателем в задачу может любой зарегистрированный пользователь:

- PUT-запрос
    ```http request
    http://localhost:8080/api/{taskId}/addObserved
    ```
  Request body: text, id the observing user

6. Удалить задачу по ID, (может только пользователь с ролью ROLE_MANAGER):

- DELETE-запрос:
    ```http request
    http://localhost:8080/api/task/{taskId}
    ```

## До запуска приложений

Необходимо запустить docker-compose.

```
~/TaskTracker/docker$ docker-compose up
```