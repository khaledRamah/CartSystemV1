
myApp.controller("itemsController",function($scope,itemResource,$rootScope,SharedMethods){


   var itemToCart ={id:0,myItem:{}};
   function doReset(){
        $scope.newShopItem = {id: 0, name: "", price: "",description: ""};
   }
   function GetItems(){
        itemResource.query().$promise.then(
            function(response){
                $scope.shopItemsList =response;
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

    $scope.userFullCart = SharedMethods.getCart();

    $scope.removeFromShoppingCart =function (index){
        SharedMethods.deleteSoldItem(index);
        $scope.userFullCart = SharedMethods.getCart()
     };

    $scope.confirm =function(){
        var minCart = SharedMethods.getCart().userCart;
        createUserCart(minCart);
    }

    function createUserCart(cart){
        cartResource.save(cart).$promise.then(
            function(response){ $scope.userFullCart.userCart.id = response.id;
            setFullCart();
            },
            function(Error){ console.log("Error", Error) });
    }

    function setFullCart(){
        fullCartResource.save( $scope.userFullCart).$promise.then(
            function(){
                SharedMethods.reset();
                $scope.userFullCart = SharedMethods.getCart();
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
           },
           function(Error){
                 // view that account was not created
                 Warning.style.display = "block";
           });
   }

   $scope.login =function(){
        userResource.query({email: $scope.userData.email}).$promise.then(
            function(response){

                if(response[0].password == $scope.userData.password)
                {
                    signUp.style.display = "none";
                    login.style.display = "none";
                    logOut.style.display = "block";

                    SharedMethods.setId(response[0].id);
                    $scope.userData=doReset();
                    $state.go('itemsList');
                }
             },
            function(Error){
            console.log(Error)
                // view that account was not created
                Warning.style.display = "block";
            });
   }
})
