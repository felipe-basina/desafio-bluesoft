;(function() {
    'use strict';

    angular
        .module('app')
        .directive('listaDePedidos', [function () {
            return {
                restrict: 'E',
                templateUrl: 'components/directives/listaDePedidos.html',
                scope: {
                    'pedidos': '='
                }
            }
        }]);

})();
