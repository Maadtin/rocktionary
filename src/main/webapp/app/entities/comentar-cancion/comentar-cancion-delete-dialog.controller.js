(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarCancionDeleteController',ComentarCancionDeleteController);

    ComentarCancionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ComentarCancion'];

    function ComentarCancionDeleteController($uibModalInstance, entity, ComentarCancion) {
        var vm = this;

        vm.comentarCancion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ComentarCancion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
