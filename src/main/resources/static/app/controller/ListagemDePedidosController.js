;(function() {
    angular
        .module('app')
        .controller('ListagemDePedidosController', ['SweetAlert', 'PedidoService', function(SweetAlert, PedidoService) {
            var vm = this;

            function init() {
                PedidoService.get().then(function(response) {
                    vm.pedidos = response.data.pedidos;
                }, function(response) {
                    SweetAlert.swal({
                        title: 'Erro ao carregar pedidos',
                        text: response.data.mensagem,
                        type: 'error'
                    });
                });
            }

            init();

            return vm;
        }]);
})();
