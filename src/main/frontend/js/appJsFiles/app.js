var myApp=angular.module("appModule",['ui.router','ngResource'])

myApp.config(function($stateProvider,$urlRouterProvider)
{
    $urlRouterProvider.otherwise('/hiPage');
    $stateProvider
        .state("hiPage",{
            url :"/hiPage",
            controller :'',
            templateUrl :'hiPage.html'
        })

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

        .state("login",{
            url :"/login",
            controller :'userController',
            templateUrl :'login.html'
        })

        .state("admin",{
            url :"/admin",
            controller :'',
            templateUrl :'admin.html'
        })
        .state("admin.addItem",{
            url :"/newItem",
            controller :'itemsController',
            templateUrl :'itemDetails.html'
        })
})
