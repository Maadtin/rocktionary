(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComponenteDeleteController',ComponenteDeleteController);

    ComponenteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Componente'];

    function ComponenteDeleteController($uibModalInstance, entity, Componente) {
        var vm = this;

        vm.componente = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Componente.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
