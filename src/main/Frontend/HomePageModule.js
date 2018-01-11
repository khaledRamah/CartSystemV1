var HomePageModule = angular.module('MainModule',['ngResource']);

angular.module('MainModule').factory('cartResource', function($resource) {
  return $resource('http://localhost:8080/cart/:cartId',
  {cartId :'@cartId' },
  { update: { method: 'PUT' }},
   { headers : { 'Origin': 'http://127.0.0.1:8080' }} ); // Note the full endpoint address
});

angular.module('MainModule').factory('itemResource', function($resource) {
  return $resource('http://localhost:8080/Items',
   { headers : { 'Origin': 'http://127.0.0.1:8080' }} ); // Note the full endpoint address
});

var MyController=HomePageModule.controller('MainController',function($scope, $resource,cartResource,itemResource)
{
    var ResetCart= function(){
    return {
            "Id": -1,
            "TotalPrice": 0,
            "ItemsList": []
            }
    };

    var ResetItemsList =  function(){
   return {"items":{
   "12":{"Name":"Item 12","Price":12,"Description":"Description 12"},
    "8":{"Name":"Item 8","Price":8,"Description":"Description 8"},
    "4":{"Name":"Item 4","Price":4,"Description":"Description 4"} }}};


    $scope.CurrentCartId = function () { return document.getElementById("TxtCartID").value};
    $scope.ShoppingCart = ResetCart();
    $scope.ShopItemsList=ResetItemsList().items;

    itemResource.get().$promise.then(function(Responce)
       {
           $scope.ShopItemsList=Responce.items
         // console.log("Items",Responce.items)
         },function(Error){
    console.log("Error", Error)
    });

// add new item to my cart
    $scope.AddItemToCart = function(AddedItemId,ToRemoveIndex) {
        $scope.ShoppingCart.TotalPrice += $scope.ShopItemsList[AddedItemId].Price;
        $scope.ShoppingCart.ItemsList.push(  parseInt(AddedItemId) );
    };

    $scope.RemoveFromShoppingCart =function (RemovedItemId,Index){
        $scope.ShoppingCart.TotalPrice -= $scope.ShopItemsList[RemovedItemId].Price;
        $scope.ShoppingCart.ItemsList.splice(Index,1)
     };

    $scope.SaveCart=function() {
        $scope.ShoppingCart.Id=parseInt($scope.CurrentCartId())

         $scope._SaveCart($scope.ShoppingCart)
    }

    $scope.GetCart= function() { $scope._GetCart($scope.CurrentCartId())}

    $scope.UpdateCart = function() {
    $scope._UpdateCart($scope.CurrentCartId(),$scope.ShoppingCart)
    }
    $scope.DeleteCart=function() {$scope._DeleteCart($scope.CurrentCartId())}



      $scope._GetCart =function(_CartId){
              cartResource.get({cartId: _CartId}).$promise.then(function(MyCart)
                     {
                         $scope.ShoppingCart = MyCart
                     },function(Error){
                         console.log("Error", Error)
                  });
         }

      $scope._UpdateCart = function(_CartId,NewCart){

            cartResource.update({cartId :_CartId} ,NewCart),
                                 function(Response){
                                 document.getElementById("TxtCartID").value= Response.id
                                 },
                                 function(Error){
                                 console.log("Error",Error)
                              };
                              $scope.ShoppingCart=ResetCart();
         }

      $scope._SaveCart = function (CreatedCart){
             cartResource.save(CreatedCart).$promise.then(
                     function(Response){

                     },
                     function(Error){
                     console.log("Error",Error)
                  });
                  $scope.ShoppingCart=ResetCart();
         }

      $scope._DeleteCart = function (_CartId){
            cartResource.delete({cartId: _CartId}),(function()
               {

               },function(Error){
               console.log("Error", Error)
                });
                $scope.ShoppingCart=ResetCart();

        }





})

MyController.directive('buttonsList', function() {
    return {
      restrict: 'E',
      templateUrl: 'HTML/ButtonsList.html'
    };
  });
MyController.directive('allItems',function()
{
    return {
          restrict: 'E',
          templateUrl: 'HTML/AllItems.html'
    };
});
MyController.directive('allCart',function()
{
    return{
        restrict: 'E',
        templateUrl: 'HTML/AllCart.html'
    };
});