angular.module('app',[]).controller('indexController',function($scope,$http){
    const contextPath = 'http://localhost:8090/app';

    $scope.fillTable = function(){
        $http.get(contextPath + '/products')
        .then(function(response){
            $scope.ProductList = response.data;
        });

    };
    $scope.deleteProduct = function(productId){
            $http.get(contextPath + '/products/delete/' + productId)
                    .then(function(response){
                        $scope.fillTable();
                    });
    }

    $scope.changeProductPrice = function(productId, delta){
        $http({
                url: contextPath + '/products/change_price',
                method: 'GET',
                params: {
                    productId: productId,
                    delta: delta
                }
                })
               .then(function(response){
                            $scope.fillTable();
                });
    }

    $scope.addNewProduct = function(title,price,id){
            $http({
                    url: contextPath + '/products/add',
                    method: 'GET',
                    params: {
                        title: title,
                        price: price,
                        id: id
                    }
            })
            .then(function(response){
                    $scope.fillTable();
            });
    }


    $scope.fillTable();
});