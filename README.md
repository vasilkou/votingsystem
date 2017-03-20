# Voting system for deciding where to have lunch
# API Reference

>1. [Users](#users)
>   - [Register a new user](#register)
>   - [Get the authenticated user](#getAuthUser)
>   - [Update the authenticated user](#updateAuthUser)
>   - [Delete the authenticated user](#deleteAuthUser)
>   - [Get all users](#getAllUsers)
>   - [Get a single user by id](#getUserById)
>   - [Get a single user by e-mail](#getUserByEmail)
>   - [Create a new user by admin](#createUser)
>   - [Update the user by admin](#updateUser)
>   - [Enable/disable the user by admin](#enableUser)
>   - [Delete the user by admin](#deleteUser)
>2. [Restaurants](#restaurants)
>   - [Get all restaurants](#getAllRestaurants)
>   - [Get a single restaurant by id](#getRestaurantById)
>   - [Get a single restaurant by name](#getRestaurantByName)
>   - [Create a new restaurant](#createRestaurant)
>   - [Update restaurant](#updateRestaurant)
>   - [Delete restaurant](#deleteRestaurant)
>3. [Menu](#menu)
>   - [Get menu](#getMenu)
>   - [Get a single dish from the menu](#getDish)
>   - [Create a new dish in the menu](#createDish)
>   - [Update the dish in the menu](#updateDish)
>   - [Delete menu](#deleteMenu)
>   - [Delete a single dish from the menu](#deleteDish)
>4. [Vote](#vote)
>   - [Vote for the restaurant](#voteForRestaurant)
>   - [Get current vote results](#getCurrentVotes)
>   - [Get vote results by date](#getVotesByDate)
>5. [Errors](#errors)



# <a name="users">1. Users</a>



## <a name="register">Register a new user</a>
Registers a new user (with role 'USER').
#### HTTP request
    POST /v1/register
#### Parameters
    No parameters.
#### Request body
    {
      "name": "register",
      "email": "register@yandex.ru",
      "password": "password"
    }
In the JSON request body, include the following object properties:
- **name** | string | **required**  
The user name.
- **email** | string | **required**  
The user e-mail.  
Must be unique.
- **password** | string | **required**  
The user password.  
Minimum length: 5.  
Maximum length: 64.
#### Success response
    Status: 200 OK
        
    {
      "id": 100021,
      "name": "register",
      "email": "register@yandex.ru",
      "password": "$2a$10$sZMYeo1YeaJPEs3dq7Iz1uG74lXO4YD0frsh/eOBQB5bAt1yGtTKG",
      "enabled": true,
      "registered": "2017-03-19T18:26:24.305+0000",
      "roles": [
        "ROLE_USER"
      ]
    }
Returns a user object in JSON format:
- **id** | number  
The ID of the user.  
- **name** | string  
The user name.  
- **email** | string  
The user e-mail (unique).  
- **password** | string  
The encoded user password.
- **enabled** | boolean  
The user status, user may be enabled (true) or disabled (false).
- **registered** | string  
The date and time when the user was created, in ISO 8601 format.
- **roles** | array of enums  
An array of user roles.  
`["ROLE_USER"]` - is the regular user.
#### Error response
Status: 409 Conflict.  
Returns an Error JSON object ([see here](#errors)).  

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X POST http://localhost:8080/votingsystem/v1/register \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "name": "register",
          "email": "register@yandex.ru",
          "password": "password"
        }'



## <a name="getAuthUser">Get the authenticated user</a>
Lists profile information when authenticated through basic auth and authorized as a user.
#### HTTP request
    GET /v1/profile
#### Parameters
    No parameters.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    {
      "id": 100000,
      "name": "User",
      "email": "user@yandex.ru",
      "password": "$2a$10$l/0pK93oK/nSg/CL0bJsC.FjHhkLJki7JrqtbUyqiwwYeY9uMs1Me"
    }
#### Error response
Status: 401 Unauthorized
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/profile --user user@yandex.ru:password



## <a name="updateAuthUser">Update the authenticated user</a>
Updates profile information of the authenticated user (require basic auth).
#### HTTP request
    PUT /v1/profile
#### Parameters
    No parameters.
#### Request body
    {
      "id": 100000,
      "name": "Updated",
      "email": "updated@yandex.ru",
      "password": "updated_password"
    }
In the JSON request body, include the following object properties:
- **id** | number  
The ID of the user to update (or authenticated user ID).
- **name** | string | **required**  
The user name.
- **email** | string | **required**  
The user e-mail.  
Must be unique.
- **password** | string | **required**  
The user password.  
Minimum length: 5.  
Maximum length: 64.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 409 Conflict.  
Returns an Error JSON object ([see here](#errors)).  

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X PUT http://localhost:8080/votingsystem/v1/profile --user user@yandex.ru:password \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "id": 100000,
          "name": "Updated",
          "email": "updated@yandex.ru",
          "password": "updated_password"
        }'



## <a name="deleteAuthUser">Delete the authenticated user</a>
Deletes the authenticated user profile (require basic auth).
#### HTTP request
    DELETE /v1/profile
#### Parameters
    No parameters.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized
#### Sample call
    curl -s -X DELETE http://localhost:8080/votingsystem/v1/profile --user user@yandex.ru:password



## <a name="getAllUsers">Get all users</a>
Lists all users  when authenticated through basic auth and authorized as an admin.
#### HTTP request
    GET /v1/admin/users
#### Parameters
    No parameters.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    [
      {
        "id": 100001,
        "name": "Admin",
        "email": "admin@gmail.com",
        "password": "$2a$10$VqJ4z0/59AUGaZu8x4HXiO6ZNG.aC.WJOQTzd1ilQM1GQ9HEJngJO",
        "enabled": true,
        "registered": "2017-03-19T21:28:39.167+0000",
        "roles": [
          "ROLE_ADMIN",
          "ROLE_USER"
        ]
      },
      {
        "id": 100000,
        "name": "User",
        "email": "user@yandex.ru",
        "password": "$2a$10$l/0pK93oK/nSg/CL0bJsC.FjHhkLJki7JrqtbUyqiwwYeY9uMs1Me",
        "enabled": true,
        "registered": "2017-03-19T21:28:39.167+0000",
        "roles": [
          "ROLE_USER"
        ]
      }
    ]
Returns an array of user objects in JSON format:
- **id** | number  
The ID of the user.  
- **name** | string  
The user name.  
- **email** | string  
The user e-mail (unique).  
- **password** | string  
The encoded user password.
- **enabled** | boolean  
The user status, user may be enabled (true) or disabled (false).
- **registered** | string  
The date and time when the user was created, in ISO 8601 format.
- **roles** | array of enums  
An array of user roles.  
Possible values:  
`["ROLE_USER"]` - is the regular user.  
`["ROLE_ADMIN", "ROLE_USER"]` - is admin.
#### Error response
Status: 401 Unauthorized
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/admin/users --user admin@gmail.com:admin



## <a name="getUserById">Get a single user by id</a>
Shows details for a user, by ID. Requires authentication through basic auth and authorization as an admin.
#### HTTP request
    GET /v1/admin/users/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the user to show.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    {
      "id": 100000,
      "name": "User",
      "email": "user@yandex.ru",
      "password": "$2a$10$l/0pK93oK/nSg/CL0bJsC.FjHhkLJki7JrqtbUyqiwwYeY9uMs1Me",
      "enabled": true,
      "registered": "2017-03-19T21:28:39.167+0000",
      "roles": [
        "ROLE_USER"
      ]
    }
Returns a user object in JSON format:
- **id** | number  
The ID of the user.  
- **name** | string  
The user name.  
- **email** | string  
The user e-mail (unique).  
- **password** | string  
The encoded user password.
- **enabled** | boolean  
The user status, user may be enabled (true) or disabled (false).
- **registered** | string  
The date and time when the user was created, in ISO 8601 format.
- **roles** | array of enums  
An array of user roles.  
Possible values:  
`["ROLE_USER"]` - is the regular user.  
`["ROLE_ADMIN", "ROLE_USER"]` - is admin.
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/admin/users/100000 --user admin@gmail.com:admin



## <a name="getUserByEmail">Get a single user by e-mail</a>
Shows details for a user, by e-mail. Requires authentication through basic auth and authorization as an admin.
#### HTTP request
    GET /v1/admin/users/with
#### Parameters
Query parameters:
- **email** | string | **required**  
The e-mail of the user to show.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    {
      "id": 100000,
      "name": "User",
      "email": "user@yandex.ru",
      "password": "$2a$10$l/0pK93oK/nSg/CL0bJsC.FjHhkLJki7JrqtbUyqiwwYeY9uMs1Me",
      "enabled": true,
      "registered": "2017-03-19T21:28:39.167+0000",
      "roles": [
        "ROLE_USER"
      ]
    }
Returns a user object in JSON format:
- **id** | number  
The ID of the user.  
- **name** | string  
The user name.  
- **email** | string  
The user e-mail (unique).  
- **password** | string  
The encoded user password.
- **enabled** | boolean  
The user status, user may be enabled (true) or disabled (false).
- **registered** | string  
The date and time when the user was created, in ISO 8601 format.
- **roles** | array of enums  
An array of user roles.  
Possible values:  
`["ROLE_USER"]` - is the regular user.  
`["ROLE_ADMIN", "ROLE_USER"]` - is admin.
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/admin/users/with?email=user@yandex.ru \
    --user admin@gmail.com:admin



## <a name="createUser">Create a new user by admin</a>
Creates a new user by admin (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    POST /v1/admin/users
#### Parameters
    No parameters.
#### Request body
    {
      "name": "new name",
      "email": "new@gmail.com",
      "password": "wasd666",
      "enabled": true,
      "roles": [
        "ROLE_USER",
        "ROLE_ADMIN"
      ]
    }
In the JSON request body, include the following object properties:
- **name** | string | **required**  
The user name.
- **email** | string | **required**  
The user e-mail.  
Must be unique.
- **password** | string | **required**  
The user password.  
Minimum length: 5.  
Maximum length: 64.
- **enabled** | boolean  
The user status, user may be enabled (true) or disabled (false).  
Default: true.
- **roles** | array of enums | **required**  
An array of user roles.  
Possible values:  
`["ROLE_USER"]` - is the regular user.  
`["ROLE_ADMIN", "ROLE_USER"]` - is admin.
#### Success response
    Status: 201 Created
        
    {
      "id": 100022,
      "name": "new name",
      "email": "new@gmail.com",
      "password": "$2a$10$7C3CmyGJ9wyMBAkht0dw.ecp2zdRJ8GIujN03u56P8Vhpqxqj0wxW",
      "enabled": true,
      "registered": "2017-03-19T22:14:26.536+0000",
      "roles": [
        "ROLE_ADMIN",
        "ROLE_USER"
      ]
    }
Returns a user object in JSON format:
- **id** | number  
The ID of the user.  
- **name** | string  
The user name.  
- **email** | string  
The user e-mail (unique).  
- **password** | string  
The encoded user password.
- **enabled** | boolean  
The user status, user may be enabled (true) or disabled (false).
- **registered** | string  
The date and time when the user was created, in ISO 8601 format.
- **roles** | array of enums  
An array of user roles.  
Possible values:  
`["ROLE_USER"]` - is the regular user.  
`["ROLE_ADMIN", "ROLE_USER"]` - is admin.
#### Error response
Status: 401 Unauthorized

Status: 409 Conflict.  
Returns an Error JSON object ([see here](#errors)).  

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X POST http://localhost:8080/votingsystem/v1/admin/users --user admin@gmail.com:admin \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "name": "new name",
          "email": "new@gmail.com",
          "password": "wasd666",
          "enabled": true,
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ]
        }'



## <a name="updateUser">Update the user by admin</a>
Updates the user details by admin (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    PUT /v1/admin/users/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the user to update.
#### Request body
    {
      "id": 100000,
      "name": "updated name",
      "email": "updated@yandex.ru",
      "password": "updated_pwd",
      "enabled": true,
      "roles": [
        "ROLE_USER",
        "ROLE_ADMIN"
      ]
    }
In the JSON request body, include the following object properties:
- **id** | number  
The ID of the user to update.
- **name** | string | **required**  
The user name.
- **email** | string | **required**  
The user e-mail.  
Must be unique.
- **password** | string | **required**  
The user password.  
Minimum length: 5.  
Maximum length: 64.
- **enabled** | boolean  
The user status, user may be enabled (true) or disabled (false).
- **roles** | array of enums | **required**  
An array of user roles.  
Possible values:  
`["ROLE_USER"]` - is the regular user.  
`["ROLE_ADMIN", "ROLE_USER"]` - is admin.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 409 Conflict.  
Returns an Error JSON object ([see here](#errors)).  

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X PUT http://localhost:8080/votingsystem/v1/admin/users/100000 --user admin@gmail.com:admin \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "id": 100000,
          "name": "Updated User",
          "email": "user@yandex.ru",
          "password": "password",
          "enabled": true,
          "roles": [
            "ROLE_USER",
            "ROLE_ADMIN"
          ]
        }'



## <a name="enableUser">Enable/disable the user by admin</a>
Enables and disables the user by admin (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    PATCH /v1/admin/users/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the user to enable/disable.

Query parameters:
- **enabled** | boolean | **required**  
Possible values:  
`true` - enable user;  
`false` - disable user.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X PATCH http://localhost:8080/votingsystem/v1/admin/users/100000?enabled=false \
    --user admin@gmail.com:admin



## <a name="deleteUser">Delete the user by admin</a>
Deletes the user by admin (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    DELETE /v1/admin/users/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the user to delete.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X DELETE http://localhost:8080/votingsystem/v1/admin/users/100000 \
    --user admin@gmail.com:admin



# <a name="restaurants">2. Restaurants</a>



## <a name="getAllRestaurants">Get all restaurants</a>
Lists all restaurants sorted by name with their menus, dishes in menus are also sorted by name
(required authentication through basic auth and authorization as a user).
#### HTTP request
    GET /v1/restaurants
#### Parameters
    No parameters.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    [
      {
        "id": 100004,
        "name": "Chaihana",
        "address": "154 Nezavisimosti Avenue",
        "phoneNumber": "+375 (29) 245-32-45",
        "menu": [
          {
            "id": 100010,
            "name": "Kalyan",
            "price": 32.5
          },
          {
            "id": 100011,
            "name": "Pahlava",
            "price": 15.1
          }
        ],
        "selected": false
      },
      {
        "id": 100002,
        "name": "Susi Vesla",
        "address": "12 Russianova st.",
        "phoneNumber": "260-15-98",
        "menu": [
          {
            "id": 100006,
            "name": "Sashimi",
            "price": 25
          },
          {
            "id": 100005,
            "name": "Sushi",
            "price": 12.25
          }
        ],
        "selected": true
      }
    ]
Returns an array of restaurant objects with menus in JSON format:
- **id** | number  
The ID of the restaurant.  
- **name** | string  
The restaurant name.  
- **address** | string  
The restaurant address.  
- **phoneNumber** | string  
The restaurant phone number.
- **menu** | array of dish objects  
An array of dishes (or menu), where:
  * **id** | number  
  The ID of the dish.
  * **name** | string  
  The name of the dish.
  * **price** | number  
  The price of the dish.  
- **selected** | boolean  
Shows if the authenticated user has already voted (true) for the restaurant today or not (false).
#### Error response
Status: 401 Unauthorized
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/restaurants --user user@yandex.ru:password



## <a name="getRestaurantById">Get a single restaurant by id</a>
Shows details for the restaurant, by ID. Requires authentication through basic auth and authorization as an admin.
#### HTTP request
    GET /v1/admin/restaurants/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the restaurant to show.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    {
      "id": 100002,
      "name": "Susi Vesla",
      "address": "12 Russianova st.",
      "phoneNumber": "260-15-98"
    }
Returns a restaurant object in JSON format:
- **id** | number  
The ID of the restaurant.  
- **name** | string  
The restaurant name.  
- **address** | string  
The restaurant address.  
- **phoneNumber** | string  
The restaurant phone number.
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/admin/restaurants/100002 --user admin@gmail.com:admin



## <a name="getRestaurantByName">Get a single restaurant by name</a>
Shows details for the restaurant, by name. Requires authentication through basic auth and authorization as an admin.
#### HTTP request
    GET /v1/admin/restaurants/with
#### Parameters
Query parameters:
- **name** | string | **required**  
The name of the restaurant to show.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    {
      "id": 100002,
      "name": "Susi Vesla",
      "address": "12 Russianova st.",
      "phoneNumber": "260-15-98"
    }
Returns a restaurant object in JSON format:
- **id** | number  
The ID of the restaurant.  
- **name** | string  
The restaurant name.  
- **address** | string  
The restaurant address.  
- **phoneNumber** | string  
The restaurant phone number.
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/admin/restaurants/with?name=Susi+Vesla \
    --user admin@gmail.com:admin



## <a name="createRestaurant">Create a new restaurant</a>
Creates a new restaurant by admin (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    POST /v1/admin/restaurants
#### Parameters
    No parameters.
#### Request body
    {
      "name": "new restaurant",
      "address": "Some street, 222",
      "phoneNumber": "+375 (29) 222-33-44"
    }
In the JSON request body, include the following object properties:
- **name** | string | **required**  
The name of the restaurant.
- **address** | string | **required**  
The address of the restaurant.  
"Name + Address" must be unique.
- **phoneNumber** | string | **required**  
The phone number of the restaurant.  
#### Success response
    Status: 201 Created
        
    {
      "id": 100021,
      "name": "new restaurant",
      "address": "Some street, 222",
      "phoneNumber": "+375 (29) 222-33-44"
    }
Returns a restaurant object in JSON format:
- **id** | number  
The ID of the restaurant.  
- **name** | string  
The restaurant name.  
- **address** | string  
The restaurant address.  
- **phoneNumber** | string  
The restaurant phone number.
#### Error response
Status: 401 Unauthorized

Status: 409 Conflict.  
Returns an Error JSON object ([see here](#errors)).  

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X POST http://localhost:8080/votingsystem/v1/admin/restaurants --user admin@gmail.com:admin \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "name": "new restaurant",
          "address": "Some street, 222",
          "phoneNumber": "+375 (29) 222-33-44"
        }'



## <a name="updateRestaurant">Update restaurant</a>
Updates the restaurant details by admin (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    PUT /v1/admin/restaurants/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the restaurant to update.
#### Request body
    {
      "id": 100002,
      "name": "Updated Name",
      "address": "Updated street, 666",
      "phoneNumber": "222-33-44"
    }
In the JSON request body, include the following object properties:
- **id** | number  
The ID of the user to update.
- **name** | string | **required**  
The name of the restaurant.
- **address** | string | **required**  
The address of the restaurant.  
"Name + Address" must be unique.
- **phoneNumber** | string | **required**  
The phone number of the restaurant. 
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 409 Conflict.  
Returns an Error JSON object ([see here](#errors)).  

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X PUT http://localhost:8080/votingsystem/v1/admin/restaurants/100002 --user admin@gmail.com:admin \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "id": 100002,
          "name": "Updated Name",
          "address": "Updated street, 666",
          "phoneNumber": "222-33-44"
        }'



## <a name="deleteRestaurant">Delete restaurant</a>
Deletes the restaurant, by ID (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    DELETE /v1/admin/restaurants/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the restaurant to delete.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X DELETE http://localhost:8080/votingsystem/v1/admin/restaurants/100002 \
    --user admin@gmail.com:admin



# <a name="menu">3. Menu</a>



## <a name="getMenu">Get menu</a>
Shows a list of dishes (menu) for the restaurant, by the restaurant ID. Dishes in the menu are sorted by their name.  
Requires authentication through basic auth and authorization as an admin.
#### HTTP request
    GET /v1/admin/restaurants/:id/dishes
#### Parameters
Path parameters:
- **id** | string  
The ID of the restaurant.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    [
      {
        "id": 100006,
        "name": "Sashimi",
        "price": 25
      },
      {
        "id": 100005,
        "name": "Sushi",
        "price": 12.25
      }
    ]
Returns an array of the dish objects in JSON format:
- **id** | number  
The ID of the dish in menu.  
- **name** | string  
The name of the dish in menu.  
- **price** | number  
The price of the dish in menu.
#### Error response
Status: 401 Unauthorized
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/admin/restaurants/100002/dishes --user admin@gmail.com:admin



## <a name="getDish">Get a single dish from the menu</a>
Shows details for the dish, by ID. Requires authentication through basic auth and authorization as an admin.
#### HTTP request
    GET /v1/admin/restaurants/:restaurantID/dishes/:id
#### Parameters
Path parameters:
- **restaurantID** | string  
The ID of the restaurant.
- **id** | string  
The ID of the dish to show.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    {
      "id": 100005,
      "name": "Sushi",
      "price": 12.25
    }
Returns the dish object in JSON format:
- **id** | number  
The ID of the dish.  
- **name** | string  
The name of the dish.  
- **price** | number  
The price of the dish.
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/admin/restaurants/100002/dishes/100005 \
    --user admin@gmail.com:admin



## <a name="createDish">Create a new dish in the menu</a>
Adds a new dish to the menu of the restaurant (requires authentication through basic auth and
authorization as an admin).
#### HTTP request
    POST /v1/admin/restaurants/:id/dishes
#### Parameters
Path parameters:
- **id** | string  
The ID of the restaurant.
#### Request body
    {
      "name": "new dish",
      "price": 666.66
    }
In the JSON request body, include the following object properties:
- **name** | string | **required**  
The name of the dish.
- **price** | number | **required**  
The price of the dish.  
Minimum value: 0.00.
#### Success response
    Status: 201 Created
        
    {
      "id": 100021,
      "name": "new dish",
      "price": 666.66
    }
Returns the dish object in JSON format:
- **id** | number  
The ID of the dish.  
- **name** | string  
The name of the dish.  
- **price** | number  
The price of the dish.
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X POST http://localhost:8080/votingsystem/v1/admin/restaurants/100002/dishes \
    --user admin@gmail.com:admin \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "name": "new dish",
          "price": 666.66
        }'



## <a name="updateDish">Update the dish in the menu</a>
Updates the dish details, by ID (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    PUT /v1/admin/restaurants/:restaurantID/dishes/:id
#### Parameters
Path parameters:
- **restaurantID** | string  
The ID of the restaurant.
- **id** | string  
The ID of the dish to update.
#### Request body
    {
      "id": 100005,
      "name": "Updated dish",
      "price": 10.59
    }
In the JSON request body, include the following object properties:
- **id** | number  
The ID of the dish to update.
- **name** | string | **required**  
The name of the dish.
- **price** | number | **required**  
The price of the dish.  
Minimum value: 0.00.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X PUT http://localhost:8080/votingsystem/v1/admin/restaurants/100002/dishes/100005 \
    --user admin@gmail.com:admin \
    -H "Content-Type:application/json;charset=UTF-8" \
    -d '{
          "id": 100005,
          "name": "Updated dish",
          "price": 10.59
        }'



## <a name="deleteMenu">Delete menu</a>
Deletes the menu (a list of dishes) of the restaurant, by the restaurant ID
(requires authentication through basic auth and authorization as an admin).
#### HTTP request
    DELETE /v1/admin/restaurants/:id/dishes
#### Parameters
Path parameters:
- **id** | string  
The ID of the restaurant.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X DELETE http://localhost:8080/votingsystem/v1/admin/restaurants/100002/dishes \
    --user admin@gmail.com:admin



## <a name="deleteDish">Delete a single dish from the menu</a>
Deletes a single dish from the menu, by ID (requires authentication through basic auth and authorization as an admin).
#### HTTP request
    DELETE /v1/admin/restaurants/:restaurantID/dishes/:id
#### Parameters
Path parameters:
- **restaurantID** | string  
The ID of the restaurant.
- **id** | string  
The ID of the dish to delete.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X DELETE http://localhost:8080/votingsystem/v1/admin/restaurants/100002/dishes/100005 \
    --user admin@gmail.com:admin



# <a name="vote">4. Vote</a>



## <a name="voteForRestaurant">Vote for the restaurant</a>
Votes for the restaurant (requires authentication through basic auth and authorization as a user).
  
If the user votes again the same day:  
- If it is before 11:00 we assume that he changed his mind.  
- If it is after 11:00 then it is too late, vote can't be changed.
#### HTTP request
    PUT /v1/votes/restaurants/:id
#### Parameters
Path parameters:
- **id** | string  
The ID of the restaurant.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
#### Error response
Status: 401 Unauthorized

Status: 403 Forbidden.  
Returns an Error JSON object ([see here](#errors)).

Status: 422 Unprocessable Entity.  
Returns an Error JSON object ([see here](#errors)).
#### Sample call
    curl -s -X PUT http://localhost:8080/votingsystem/v1/votes/restaurants/100002 \
    --user user@yandex.ru:password



## <a name="getCurrentVotes">Get current vote results</a>
Lists current vote results (requires authentication through basic auth and authorization as a user).
#### HTTP request
    GET /v1/votes
#### Parameters
    No parameters.
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    [
      {
        "restaurantName": "Шоколадница",
        "votes": 2,
        "date": "2017-03-20"
      },
      {
        "restaurantName": "Susi Vesla",
        "votes": 1,
        "date": "2017-03-20"
      }
    ]
Returns an array of the votes result objects in JSON format:
- **restaurantName** | string  
The name of the restaurant.  
- **votes** | number  
The number of votes for the restaurant.  
- **date** | string  
The date of the vote (in ISO 8601 format).
#### Error response
Status: 401 Unauthorized
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/votes --user user@yandex.ru:password



## <a name="getVotesByDate">Get vote results by date</a>
Lists vote results by date (requires authentication through basic auth and authorization as a user).
#### HTTP request
    GET /v1/votes/with
#### Parameters
Query parameters:
- **date** | string | **required**  
The date of the vote results (in ISO 8601 format).
#### Request body
    Do not supply a request body with this method.
#### Success response
    Status: 200 OK
        
    [
      {
        "restaurantName": "Susi Vesla",
        "votes": 1,
        "date": "2017-02-27"
      },
      {
        "restaurantName": "Шоколадница",
        "votes": 1,
        "date": "2017-02-27"
      }
    ]
Returns an array of the votes result objects in JSON format:
- **restaurantName** | string  
The name of the restaurant.  
- **votes** | number  
The number of votes for the restaurant.  
- **date** | string  
The date of the vote (in ISO 8601 format).
#### Error response
Status: 401 Unauthorized
#### Sample call
    curl -s http://localhost:8080/votingsystem/v1/votes/with?date=2017-02-27 --user user@yandex.ru:password



# <a name="errors">5. Errors</a>
This API returns standard HTTP status codes for error responses.  

The response body for all errors except Identity errors includes additional error details in this format:

    {
      "url": "http://localhost:8080/votingsystem/v1/register",
      "cause": "ValidationException",
      "details": [
        "password size must be between 5 and 64",
        "name may not be empty",
        "email not a well-formed email address"
      ]
    }
Where:  
- **url** | stirng  
Error documentation link.  
- **cause** | string  
Error name.  
- **details** | array of strings  
Error detailed description.