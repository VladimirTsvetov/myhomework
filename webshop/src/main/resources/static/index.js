angular.module('app',[]).controller('indexController',function($scope,$http){
    const contextPath = 'http://localhost:8189/shop/api/v1/products/';


    $scope.fillTable = function(){
        $http.get(contextPath)
        .then(function(response){
            $scope.ProductList = response.data;
        });
    };

    $scope.getProductInfo = function(id){
            $http.get(contextPath + id)
            .then(function(response){
                alert(response.data.title);
            });
    };

    $scope.deleteProduct = function(id){
                $http.delete(contextPath + id)
                .then(function(response){
                    $scope.fillTable();
                });
            };
    $scope.addToCart = function(productId){
        $http.get('http://localhost:8189/shop/api/v1/cart/add/' + productId).
            then(function(response){
                $scope.loadCart();
         });
    }

    $scope.loadCart = function(){
        $http.get('http://localhost:8189/shop/api/v1/cart/').then(function(response){
                $scope.cart = response.data;
        });
    }

    $scope.createNewOrder = function(){
                $http.get('http://localhost:8189/shop/api/v1/order/')
                .then(function(response){
                    alert("Заказ оформлен");
                });
        };



    $scope.fillTable();
    $scope.loadCart();
});
