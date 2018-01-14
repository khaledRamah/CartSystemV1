var myApp=angular.module("appModule",['ui.router','ngResource'])

myApp.config(function($stateProvider,$urlRouterProvider)
{
    $urlRouterProvider.otherwise('/allItems');
    $stateProvider
        .state("itemsList",{
        url:'/allItems',
        controller :'itemsController',
        templateUrl :'allItems.html'
        })

        .state("cartOperations",{
        url :'/allCart',
        controller : 'cartController',
        templateUrl : 'allCart.html'
        })
})
myApp.run(function($rootScope){
    var resetItemsList =  function(){
   return {
   "items":
         {
        "12":{"Name":"Item 12","Price":12,"Description":"Description 12"},
        "8":{"Name":"Item 8","Price":8,"Description":"Description 8"},
        "4":{"Name":"Item 4","Price":4,"Description":"Description 4"}
        }
     }
   };
    var resetCart= function(){
       return {
               "id": -1,
               "totalPrice": 0,
               "itemsList": []
               }
       };
    $rootScope.baseCart=resetCart();
    $rootScope.shoppingCart=resetCart();
    $rootScope.shopItemsList=resetItemsList().items;

})