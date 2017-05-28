;(function() {
    angular
        .module('app')
        .controller('AdicionarPedidoController', ['$uibModal', 'SweetAlert', 'ProdutoService', 'PedidoService', function($uibModal, SweetAlert, ProdutoService, PedidoService) {
            var vm = this;

            function init() {
                ProdutoService.get().then(function(response) {
                    vm.produtos = response.data;
                }, function(response) {
                    SweetAlert.swal({
                        title: 'Erro ao carregar produtos',
                        text: response.data.mensagem,
                        type: 'error'
                    });
                });
            }

            function salvarPedido() {
                var data = vm.produtos.map(function(produto) {
                    return {
                        gtin: produto.gtin,
                        quantidade: produto.quantidade
                    };
                });

                SweetAlert.swal({
                    title: "Carregando...",
                    showConfirmButton: false
                });

                PedidoService.save(data).then(function(response) {
                    SweetAlert.close();

                    $uibModal.open({
                       ariaLabelledBy: 'modal-title',
                       ariaDescribedBy: 'modal-body',
                       templateUrl: 'components/modals/sucessoAoAdicionarPedido.html',
                       controllerAs: 'vm',
                       controller: function() {
                           var vm = this;
                           vm.pedidos = response.data.pedidos;
                           return vm;
                       }
                   });
                }, function(response) {
                    SweetAlert.swal({
                        title: 'Erro ao criar novo pedido',
                        text: response.data.mensagem,
                        type: 'error'
                    });
                });
            }

            init();

            vm.salvarPedido = salvarPedido;
            return vm;
        }]);
})();
