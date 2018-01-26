
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
    return $resource('http://localhost:8080/user/:userId',
     {cartId :'@userId' },
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
        cart.itemsList.allSoldItemDetails = [];
        cart.totalPrice = 0 ;
    }
    var cart={
        id:0,
        itemsList:{ allSoldItemDetails:[] },
        totalPrice : 0
    };
    this.reset =function (){
        cart =doReset();
    }
    this.addSoldItem = function (newItem) {
           console.log(cart);
           cart.itemsList.allSoldItemDetails.push(newItem);
           cart.totalPrice += newItem.myItem.price;
        }
    this.deleteSoldItem =function(index){
           cart.totalPrice -= cart.itemsList.allSoldItemDetails[index].myItem.price;
           cart.itemsList.allSoldItemDetails.splice(index, 1);
    }

    this.getCart =function(){
      return cart;
    }
    this.createUserCart = function(cart){
      return cartResource.save(cart).$promise.then(
            function(response){ },
            function(Error){ console.log("Error", Error) });
    }

    this.setId =function(id){
        cart.id = id;
    }


})


