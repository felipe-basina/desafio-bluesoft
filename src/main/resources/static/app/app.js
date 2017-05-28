;(function() {
    angular
        .module('app', ['ngRoute', 'ui.bootstrap', 'oitozero.ngSweetAlert'])
        .config(['$routeProvider', function($routeProvider) {
            addRoutes($routeProvider);
        }]);

    function addRoutes($routeProvider) {
        $routeProvider
            .when('/pedidos/novo', {
                templateUrl: 'views/adicionarPedido.html',
                controller: 'AdicionarPedidoController',
                controllerAs: 'vm'
            })
            .when('/pedidos', {
                templateUrl: 'views/listagemDePedidos.html',
                controller: 'ListagemDePedidosController',
                controllerAs: 'vm'
            })
            .otherwise({
                redirectTo: '/pedidos/novo'
            });
    }
})();
