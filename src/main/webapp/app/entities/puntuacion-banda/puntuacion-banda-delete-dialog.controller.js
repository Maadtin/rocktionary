(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionBandaDeleteController',PuntuacionBandaDeleteController);

    PuntuacionBandaDeleteController.$inject = ['$uibModalInstance', 'entity', 'PuntuacionBanda'];

    function PuntuacionBandaDeleteController($uibModalInstance, entity, PuntuacionBanda) {
        var vm = this;

        vm.puntuacionBanda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PuntuacionBanda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
