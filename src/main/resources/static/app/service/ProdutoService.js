;(function() {
    angular
        .module('app')
        .service('ProdutoService', ['$http', function($http) {
            return {
                get: function() {
                    return $http.get('/api/produtos');
                }
            };
        }]);
})();
