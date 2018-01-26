
myApp.controller("itemsController",function($scope,itemResource,$rootScope,SharedMethods){


   var itemToCart ={id:0,myItem:{}};
   function doReset(){
        $scope.newShopItem = {id: 0, name: "", price: "",description: ""};
   }
   function GetItems(){
        itemResource.get().$promise.then(
            function(response){
                $scope.shopItemsList =response.items;
            },
            function(Error){console.log("Error", Error)
        });
    }

   $scope.newShopItem = {id: 0, name: "", price: "",description: ""};

   $scope.addItemToCart = function(addedItem) {
        itemToCart.myItem = addedItem;
        SharedMethods.addSoldItem(angular.copy(itemToCart));
    };

   $scope.addWebsiteItem =function(){
        console.log($scope.newShopItem);
        $scope.newShopItem.price = parseInt($scope.newShopItem.price)
        itemResource.save($scope.newShopItem).$promise.then(
            function(response){
                doReset();
                Success.style.display = "block";
            },
            function(Error){
                Warning.style.display = "block";
        });
   }

   GetItems();
})

myApp.controller("cartController",function($scope,cartResource,$rootScope,SharedMethods,fullCartResource){
    var minCart = {id  :0 ,totalPrice :0}
    $scope.userCart={
        id:0,
        itemsList:{ allSoldItemDetails:[] },
        totalPrice : 0
    };

    $scope.userCart = SharedMethods.getCart();

    $scope.removeFromShoppingCart =function (index){
        SharedMethods.deleteSoldItem(index);
        $scope.userCart = SharedMethods.getCart()
     };

    $scope.confirm =function(){
        fullCartResource.save( $scope.userCart).$promise.then(
            function(){
                SharedMethods.reset();
                $scope.userCart = SharedMethods.getCart()
            },function(Error){
                console.log("Error", Error)
        });
    }
})

myApp.controller("userController",function($scope,userResource,$state,SharedMethods){
    function doReset(){
        return {id: -1,name:'' ,email:'' ,password :''};
    }

   $scope.userData= doReset();

   $scope.registerUser =function () {
        userResource.save($scope.userData).$promise.then(
            function(response){
                // view that account created
                Success.style.display = "block";

                $scope.userData=doReset();
                var minCart = {id:response.id ,totalPrice :0};
                SharedMethods.createUserCart(minCart);
           },
           function(Error){
                 // view that account was not created
                 Warning.style.display = "block";
           });
   }

   $scope.login =function(){
        $scope.userData.id = 0;
        userResource.save($scope.userData).$promise.then(
            function(response){
                console.log(response);
                if(response.id!=0)
                {
                    signUp.style.display = "none";
                    login.style.display = "none";
                    logOut.style.display = "block";

                    SharedMethods.setId(response.id);
                    $scope.userData=doReset();
                    $state.go('itemsList');
                }
             },
            function(Error){
                // view that account was not created
                Warning.style.display = "block";
            });
   }
})
