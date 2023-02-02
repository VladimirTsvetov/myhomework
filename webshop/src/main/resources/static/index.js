angular.module('app',['ngStorage']).controller('indexController',function($scope,$http,$localStorage){
    //const contextPath = 'http://localhost:8189/shop/api/v1/products/';

   	$scope.tryToAuth=function(){
            $http.post('http://localhost:8189/shop/auth/',$scope.user)
                .then(function successCallback(response){
                if($localStorage.shopUser){
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    };
                if(response.data.token){
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.shopUser = {
                                                username: $scope.user.username,
                                                   token: response.data.token
                                             };

                    $scope.user.username = null;
                    $scope.user.password = null;


		        }
               },function errorCallback(response){
               });
       };

    $scope.tryToLogout = function(){
        $scope.clearUser();
        $scope.user = null;
        //$location.path('/');
    };

    $scope.clearUser = function(){
        delete $localStorage.shopUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function(){
        if($localStorage.shopUser){
            return true;
        }else{
            return false;
        };
    };



    $scope.fillTable = function(){
        $http.get('http://localhost:8189/shop/api/v1/products/')
        .then(function(response){
            $scope.ProductList = response.data;
        });
    };

    $scope.getProductInfo = function(id){
            $http.get('http://localhost:8189/shop/api/v1/products/' + id)
            .then(function(response){
                alert(response.data.title);
            });
    };

    $scope.deleteProduct = function(id){
                $http.delete('http://localhost:8189/shop/api/v1/products/' + id)
                .then(function(response){
                    $scope.fillTable();
                });
            };
    $scope.addToCart = function(productId){
        $http.get('http://localhost:8189/shop/api/v1/cart/add/' + productId).
            then(function(response){
                $scope.loadCart();
         });
    };

    $scope.loadCart = function(){
        $http.get('http://localhost:8189/shop/api/v1/cart/').then(function(response){
                $scope.cart = response.data;
        });
    };


    $scope.clear = function(){
         $http.get('http://localhost:8189/shop/api/v1/cart/clear/').then(function(response){
             $scope.loadCart();
         });
    };

    $scope.deleteFromCart = function(productId){
             $http.get('http://localhost:8189/shop/api/v1/cart/remove/' + productId)
                .then(function(response){
                    $scope.loadCart();
             });
    };

    $scope.increaseQuantity = function(productId){
            $http.get('http://localhost:8189/shop/api/v1/cart/add/' + productId).
                then(function(response){
                    $scope.loadCart();
             });
    };

    $scope.decreaseQuantity = function(productId){
           $http.get('http://localhost:8189/shop/api/v1/cart/delete/' + productId)
              .then(function(response){
                  $scope.loadCart();
           });
    };

    $scope.createNewOrder = function(){
            $http.post('http://localhost:8189/shop/api/v1/orders/',$localStorage.shopUser)
                .then(function successCallback(response){
                });
                alert("Заказ оформлен");
         };

    $scope.fillTable();
    $scope.loadCart();


});
