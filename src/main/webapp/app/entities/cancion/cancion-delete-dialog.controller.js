(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('CancionDeleteController',CancionDeleteController);

    CancionDeleteController.$inject = ['$uibModalInstance', 'entity', 'Cancion'];

    function CancionDeleteController($uibModalInstance, entity, Cancion) {
        var vm = this;

        vm.cancion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Cancion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
