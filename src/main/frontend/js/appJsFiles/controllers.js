
myApp.controller("itemsController",function($scope,itemResource,$rootScope){

     itemResource.get().$promise.then(
        function(response){
          $rootScope.shopItemsList = response.items;
        },function(Error){
          console.log("Error", Error)
        });

   $scope.addItemToCart = function(addedItemId) {
                $scope.shoppingCart.totalPrice += $scope.shopItemsList[addedItemId].price;
                $scope.shoppingCart.itemsList.push(  parseInt(addedItemId) );
            };

})

myApp.controller("cartController",function($scope,cartResource,$rootScope){
    var doResetAfterAnyOperation= function()
    {
        $rootScope.shoppingCart=angular.copy($rootScope.baseCart);
        $scope.userCart=angular.copy($rootScope.baseCart);
    }

    $scope.idValue=0;
    $scope.userCart;

    $scope.removeFromShoppingCart =function (removedItemId,index,cartToDeleteFrom){
        cartToDeleteFrom.totalPrice -= $scope.shopItemsList[removedItemId].price;
        cartToDeleteFrom.itemsList.splice(index,1)
     };

    $scope.getCart =function(cartId){
       cartResource.get({cartId: cartId}).$promise.then(function(myCart)
         {
            $scope.userCart= myCart;
         },function(Error){
            console.log("Error", Error)
        });
    }

    $scope.updateCart = function(cartId){

        $scope.userCart.totalPrice += $scope.shoppingCart.totalPrice;
          for(var itemId in $scope.shoppingCart.itemsList)
            $scope.userCart.itemsList.push($scope.shoppingCart.itemsList[itemId])

            cartResource.update({cartId :cartId} ,$scope.userCart).$promise.then(
                                 function(response){

                                 },
                                 function(Error){
                                 console.log("Error",Error)
                              });
 doResetAfterAnyOperation();
         }

    $scope.saveCart = function (createdCart){
            createdCart.id= parseInt($scope.idValue);
             cartResource.save(createdCart).$promise.then(
                     function(response){
                       doResetAfterAnyOperation();
                     },
                     function(Error){
                     console.log("Error",Error)
                  });

         }

    $scope.deleteCart = function (cartId){
            cartResource.delete({cartId: cartId}).$promise.then(
               function(response){
                    doResetAfterAnyOperation()
               },function(Error){
               console.log("Error", Error)
                });


        }
})
/*.directive('cartDirective', function() {
    return {
        restrict: 'E',
        scope :{
            cartDirectiveInfo : '=info',
            shopItemsList :'=items'
        },
        templateUrl: '../html/cartItems.html'
    };
});*/

