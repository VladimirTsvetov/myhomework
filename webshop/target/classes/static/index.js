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

    $scope.fillTable();
});
