(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('DiscograficaDeleteController',DiscograficaDeleteController);

    DiscograficaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Discografica'];

    function DiscograficaDeleteController($uibModalInstance, entity, Discografica) {
        var vm = this;

        vm.discografica = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Discografica.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
