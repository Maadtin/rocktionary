(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarBandaDeleteController',ComentarBandaDeleteController);

    ComentarBandaDeleteController.$inject = ['$uibModalInstance', 'entity', 'ComentarBanda'];

    function ComentarBandaDeleteController($uibModalInstance, entity, ComentarBanda) {
        var vm = this;

        vm.comentarBanda = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ComentarBanda.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
