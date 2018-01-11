

describe('Cart factory', function() {


beforeEach(function() {

		angular.mock.module('MainModule');


	});
describe('TxController', function() {


        //ShoppingCart =
        var controller;
		var scope;
       beforeEach(inject(function($controller, $rootScope) {

       			scope = $rootScope.$new();
       			scope.logAjaxError = function(data, status, headers, config) {};

       			controller = $controller('MainController', {
       				$scope: scope

       			});
       		}));

       describe("Test controller",function(){
            it('Controller should be defined', function() {
                expect(controller).toBeDefined();
            });
		});

		describe("Test controller",function()
		{
            it('List of items are here ', function() {
                 expect(Object.keys(scope.ShopItemsList).length).toEqual(3);
            });
            it ('Add 2 items to cart',function(){
                scope.AddItemToCart(8);
                scope.AddItemToCart(4);
                scope.AddItemToCart(4);

                expect(scope.ShoppingCart.TotalPrice).toEqual(16);
            });
            it ('Remove items from Cart',function(){
                scope.AddItemToCart(8);
                scope.AddItemToCart(4);
                scope.AddItemToCart(4);

                scope.RemoveFromShoppingCart(4,1);
                expect(scope.ShoppingCart.TotalPrice).toEqual(12);

            });
    	});
    });
});