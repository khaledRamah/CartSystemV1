# CartSystem

To start the project run main.scala .

open start.html , register then login 

- To register new user :
curl -v -H "Content-Type: application/json"  -X POST http://localhost:8080/user -d '{"id":0,"name":"myName" ,"email":"e.ex@gmail.com","password" :"123456"}'

- To find user where e.ex@gmail.com is usermail
curl -v -H "Content-Type: application/json"  get http://localhost:8080/user/e.ex@gmail.com

- To add items to the website so user can select from them
curl -v -H "Content-Type: application/json"  -X POST http://localhost:8080/item -d '{"id":-1,"name":"item1" ,"price": 1,"description" :"des 1"}

- To get all items to view them at the website
curl -v -H "Content-Type: application/json"  get http://localhost:8080/item

- To create cart for the user / replace userId with value
curl -v -H "Content-Type: application/json"  -X POST http://localhost:8080/cart -d '{"id":-1,"userId": userId ,"totalPrice":0}'

- To update the user with items / replace userId with value
curl -v -H "Content-Type: application/json"  -X POST http://localhost:8080/fullCart -d '{"userCart":{"id":1,"userId":userId,"totalPrice":3},"itemsList":{"allSoldItemDetails":[{"id":0,"myItem":{"id":1,"name":"item1","price":1,"description":"des 1"}},{"id":0,"myItem":{"id":2,"name":"item2","price":2,"description":"des 2"}}]}}'







