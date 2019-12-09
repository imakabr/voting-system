### curl samples (application deployed in application context `voting-system`).
> For windows use `Git Bash`

#### filter Meals
`curl -s "http://localhost:8080/topjava/rest/profile/meals/filter?startDate=2015-05-30&startTime=07:00:00&endDate=2015-05-31&endTime=11:00:00" --user user@yandex.ru:password`

#### get Meals not found
`curl -s -v http://localhost:8080/topjava/rest/profile/meals/100008 --user user@yandex.ru:password`


#### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/admin/users --user admin@gmail.com:admin`
`curl -s -X PUT -d '{"dateTime":"2015-05-30T07:00"}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100003 --user user@yandex.ru:password`

-------------------
### Profile

#### create profile (User)
`curl -X POST http://localhost:8080/voting/rest/profile/register -H 'Content-Type: application/json;charset=UTF-8' -d '{"name": "alex","email": "alex@ya.ru","password": "12345"}'`

#### update profile (User)
`curl -X PUT http://localhost:8080/voting/rest/profile/ -H 'Content-Type: application/json;charset=UTF-8' -d '{"name": "max","email": "alex222@ya.ru","password": "123456"}' --user alex@ya.ru:12345`

#### get user profile (User)
`curl -X GET http://localhost:8080/voting/rest/profile/ --user alex222@ya.ru:123456`

#### delete user profile (User)
`curl -X DELETE http://localhost:8080/voting/rest/profile/ --user alex222@ya.ru:123456`

------------------
### Menu

#### get all restaurants with items today
`curl -X GET http://localhost:8080/voting/rest/restaurants/today --user user@yandex.ru:password`

#### get all restaurants with items by date
`curl -X GET 'http://localhost:8080/voting/rest/restaurants/filter?date=2019-09-21' --user user@yandex.ru:password`

#### get list of restaurants
`curl -X GET http://localhost:8080/voting/rest/restaurants/list --user user@yandex.ru:password`

#### get one restaurant with items today
`curl -X GET http://localhost:8080/voting/rest/restaurants/100002/today --user user@yandex.ru:password`

#### get one restaurant with items by date
`curl -X GET 'http://localhost:8080/voting/rest/restaurants/100003/filter?date=2019-09-21' --user user@yandex.ru:password`

----------------

### Vote

#### create vote
`curl -X POST http://localhost:8080/voting/rest/restaurants/votes -H 'Content-Type: application/json;charset=UTF-8' -d '{"id": 100002,"name": "TOKYO-CITY"}' --user user@yandex.ru:password`

#### update vote
`curl -X PUT http://localhost:8080/voting/rest/restaurants/votes/100003 -H 'Content-Type: application/json;charset=UTF-8' -d '{"id": 100003,"name": "KETCH-UP"}' --user user@yandex.ru:password`

#### delete vote
`curl -X DELETE http://localhost:8080/voting/rest/restaurants/votes/100003 --user user@yandex.ru:password`

#### delete with error (old vote)
`curl -X DELETE http://localhost:8080/voting/rest/restaurants/votes/100001 --user user@yandex.ru:password`

#### get all votes for user
`curl -X GET http://localhost:8080/voting/rest/restaurants/votes/ --user user@yandex.ru:password`

#### get vote today for user
`curl -X GET http://localhost:8080/voting/rest/restaurants/votes/today --user user@yandex.ru:password`

#### get vote by date for user
`curl -X GET 'http://localhost:8080/voting/rest/restaurants/votes/filter?date=2019-09-20' --user user@yandex.ru:password`

-----------------------

### Management Users

#### get all users
`curl -X GET http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`

#### get one user by id
`curl -X GET http://localhost:8080/voting/rest/admin/users/100001 --user admin@gmail.com:admin`

#### get one user by email
`curl -X GET http://localhost:8080/voting/rest/admin/users/by?email=admin@gmail.com --user admin@gmail.com:admin`

#### create user
`curl -X POST http://localhost:8080/voting/rest/admin/users/ -H 'Content-Type: application/json;charset=UTF-8' -d '{"name": "Admin2","email": "admin2@gmail.com","password": "12345","enabled": true,"roles": ["ROLE_ADMIN","ROLE_USER"]}' --user admin@gmail.com:admin`

#### update user
`curl -X PUT http://localhost:8080/voting/rest/admin/users/100011 -H 'Content-Type: application/json;charset=UTF-8'   -d '{"name": "Admin3","email": "admin2@gmail.com","password": "12345","enabled": true,"roles": ["ROLE_ADMIN","ROLE_USER"]}' --user admin@gmail.com:admin`

#### delete user
`curl -X DELETE http://localhost:8080/voting/rest/admin/users/100009 --user admin@gmail.com:admin`

---------------------

### Management Restaurants

