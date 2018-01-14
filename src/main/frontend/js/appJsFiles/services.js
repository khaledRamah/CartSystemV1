
myApp.factory('itemResource', function($resource) {
  return $resource('http://localhost:8080/Items',
   { headers : { 'Origin': 'http://127.0.0.1:8080' }} ); // Note the full endpoint address
});

myApp.factory('cartResource', function($resource) {
  return $resource('http://localhost:8080/cart/:cartId',
  {cartId :'@cartId' },
  { update: { method: 'PUT' }},
   { headers : { 'Origin': 'http://127.0.0.1:8080' }} ); // Note the full endpoint address
});


