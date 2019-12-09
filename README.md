### curl samples (application deployed in application context `/voting`).
> For windows use `Git Bash`

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

#### get all restaurants
`curl -X GET http://localhost:8080/voting/rest/admin/restaurants/ --user admin@gmail.com:admin`

#### get restaurant
`curl -X GET http://localhost:8080/voting/rest/admin/restaurants/100002 --user admin@gmail.com:admin`

#### create restaurant
`curl -X POST http://localhost:8080/voting/rest/admin/restaurants/ -H 'Content-Type: application/json;charset=UTF-8' -d '{"name": "New Pub"}' --user admin@gmail.com:admin`

#### update restaurant
`curl -X PUT http://localhost:8080/voting/rest/admin/restaurants/100006 -H 'Content-Type: application/json;charset=UTF-8' -d '{"id": 100006,"name": "Old Pub"}' --user admin@gmail.com:admin`

#### delete restaurant
`curl -X DELETE http://localhost:8080/voting/rest/admin/restaurants/100006 --user admin@gmail.com:admin`

#### get all votes by restaurant
`curl -X GET http://localhost:8080/voting/rest/admin/restaurants/100002/votes --user admin@gmail.com:admin`

#### get all users who voted for restaurant by date
`curl -X GET http://localhost:8080/voting/rest/admin/restaurants/100002/users/filter?date=2019-09-20 --user admin@gmail.com:admin`

------------------------------

### Management Items

#### create item
`curl -X POST http://localhost:8080/voting/rest/admin/restaurants/100002/items -H 'Content-Type: application/json;charset=UTF-8' -d '{"name": "beer","price": 300,"date": "2019-12-08"}' --user admin@gmail.com:admin`

#### update item
`curl -X PUT http://localhost:8080/voting/rest/admin/restaurants/100002/items/100008 -H 'Content-Type: application/json;charset=UTF-8' -d '{"id": 100008,"name": "bebebe","price": 30,"date": "2019-10-03"}' --user admin@gmail.com:admin`

#### delete item
`curl -X DELETE http://localhost:8080/voting/rest/admin/restaurants/items/100008 --user admin@gmail.com:admin`

#### get item
`curl -X GET http://localhost:8080/voting/rest/admin/restaurants/items/100002 --user admin@gmail.com:admin`

#### get all items by restaurant
`curl -X GET http://localhost:8080/voting/rest/admin/restaurants/100002/items --user admin@gmail.com:admin`

#### get items by restaurant and date
`curl -X GET http://localhost:8080/voting/rest/admin/restaurants/100002/items/filter?date=2019-09-20 --user admin@gmail.com:admin`