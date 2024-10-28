
## Практическая работа №7
# Курса «Разработка на Spring Framework» от компании Skillbox. «Реализация приложение «Трекер задач»
## Цель практической работы
Закрепить знания, полученные по следующим темам:
<ol>
<li>Написание контроллеров с использованием Mono и Flux.</li>
<li>Работа с MongoDB в реактивной парадигме.</li>
</ol>

## Использование приложения. Документация RESTFull API

### Работа с пользователями

1. Добавление пользователя
   - POST-запрос
   
    ```http request
    http://localhost:8080/api/user
    ```
   Тело запроса:
    ```json body request
    {
        "name":"Anton",
        "email":"anton@road.lp"
    }
    ```

2. Показать всех пользователей
   - GET-запрос:
    ```http request
        http://localhost:8080/api/user
    ```

3. Поиск пользователя по ID
   - GET-запрос:
    ```http request
    http://localhost:8080/api/user/{id}
    ```
4. Удаление пользователя
    - DELETE-запрос:
    ```http request
    http://localhost:8080/api/user/{id}
    ```
5. Изменение пользователя
   - PUT-запрос

    ```http request
    http://localhost:8080/api/user/{id}
    ```

    ```json
    {
    "name":"Anton",
    "email":"anton@mail.ru"
    }
    ```
### Работа с заданиями

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
     http://localhost:8080/api/task/{id}
     ```
       Example Response:
     ```http response
       {
           "id": "671f7ec5e82ae843cfcf0802",
           "name": "Task Java Streams Reactive262",
           "description": "Java Spring Reactive Application Example, Skillbox",
           "createdAt": "2024-10-28T12:08:37Z",
           "updatedAt": "2024-10-28T12:09:51.518Z",
           "status": "TODO",
           "author": {
               "id": "67191cd9c1a39d2e3e0fc6ef",
               "userName": "Dima",
               "email": "dima@road.lp"
           },
           "assignee": {
               "id": "67191cd2c1a39d2e3e0fc6ed",
               "userName": "Anton",
               "email": "anton@road.lp"
               },
           "observers": [
                           {
                               "id": "67191ce3c1a39d2e3e0fc6f2",
                               "userName": "Leon",
                               "email": "leon@mail.org"
                           }
           ]
       }
      ```
3. Создать задачу:
- POST-запрос

    ```http request
    http://localhost:8080/api/task
    ```
  Тело запроса:
    ```json body request
    {
       "name" : "Task 2",
       "description" : "description 2",
       "authorId" : "67191cd9c1a39d2e3e0fc6ef",
       "assigneeId" : "67191ce3c1a39d2e3e0fc6f2"
    }
    ```
4. Обновить задачу:
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
5. Добавить наблюдателя в задачу:
- PUT-запрос
    ```http request
    http://localhost:8080/api/task/{id}
    ```
  Request body: text, id the observing user
  
6. Удалить задачу по ID:

- DELETE-запрос:
    ```http request
    http://localhost:8080/api/task/{id}
    ```
## До запуска приложений

Необходимо запустить docker-compose.
```
~/TaskTracker/docker$ docker-compose up
```