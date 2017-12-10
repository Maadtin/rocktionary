(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaDeleteController',BandaDeleteController);

    BandaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Banda'];

    function BandaDeleteController($uibModalInstance, entity, Banda) {
        var vm = this;

        vm.banda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Banda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
