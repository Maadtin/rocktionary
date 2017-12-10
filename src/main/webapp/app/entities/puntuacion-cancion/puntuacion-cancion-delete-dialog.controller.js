(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionCancionDeleteController',PuntuacionCancionDeleteController);

    PuntuacionCancionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PuntuacionCancion'];

    function PuntuacionCancionDeleteController($uibModalInstance, entity, PuntuacionCancion) {
        var vm = this;

        vm.puntuacionCancion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PuntuacionCancion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
