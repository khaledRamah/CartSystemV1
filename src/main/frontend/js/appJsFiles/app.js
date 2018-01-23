var myApp=angular.module("appModule",['ui.router','ngResource'])

myApp.config(function($stateProvider,$urlRouterProvider)
{
    $urlRouterProvider.otherwise('/hiPage');
    $stateProvider
        .state("itemsList",{
            url:'/allItems',
            controller :'itemsController',
            templateUrl :'allItems.html'
        })

        .state("userCart",{
            url :'/allCart',
            controller : 'cartController',
            templateUrl : 'allCart.html'
        })

        .state("signUp",{
            url :"/signUp",
            controller :'userController',
            templateUrl :'signUp.html'
        })

        .state("hiPage",{
            url :"/hiPage",
            controller :'',
            templateUrl :'hiPage.html'
        })
        .state("login",{
            url :"/login",
            controller :'userController',
            templateUrl :'login.html'
        })
})
