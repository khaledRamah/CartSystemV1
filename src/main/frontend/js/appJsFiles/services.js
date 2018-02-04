
myApp.factory('itemResource', function($resource) {
  return $resource('http://localhost:8080/item/:itemId',
   {itemId :'@itemId' },
    { update: { method: 'PUT' }},
   { headers : { 'Origin': 'http://127.0.0.1:8080' }} );
});

myApp.factory('cartResource', function($resource) {
  return $resource('http://localhost:8080/cart/:cartId',
  {cartId :'@cartId' },
  { update: { method: 'PUT' }},
   { headers : { 'Origin': 'http://127.0.0.1:8080' }} );
});

myApp.factory('userResource',function($resource){
    return $resource('http://localhost:8080/user/:email',
     {userEmail :'@email' },
     { update: { method: 'PUT' }},
     { headers : { 'Origin': 'http://127.0.0.1:8080' }} );
})

myApp.factory('fullCartResource',function($resource){
     return $resource('http://localhost:8080/fullCart',
         { },
         { update: { method: 'PUT' }},
         { headers : { 'Origin': 'http://127.0.0.1:8080' }} );
})

myApp.service('SharedMethods',function(cartResource,$rootScope){
    function doReset(){
        cart.itemsList = {allSoldItemDetails:[]} ;
        cart.userCart.id =0;
        cart.userCart.totalPrice=0;
    }
    var cart={
        userCart:{id: 0,userId: 0,totalPrice: 0},
        itemsList:{allSoldItemDetails:[]}
    };
    this.reset =function (){
        doReset();
    }
    this.addSoldItem = function (newItem) {
           console.log(cart);
           cart.itemsList.allSoldItemDetails.push(newItem);
           cart.userCart.totalPrice += newItem.myItem.price;
        }
    this.deleteSoldItem =function(index){
           cart.userCart.totalPrice -= cart.itemsList.allSoldItemDetails[index].myItem.price;
           cart.itemsList.allSoldItemDetails.splice(index, 1);
    }

    this.getCart =function(){
      return cart;
    }

    this.setId =function(id){
        cart.userCart.userId = id;
    }
})


