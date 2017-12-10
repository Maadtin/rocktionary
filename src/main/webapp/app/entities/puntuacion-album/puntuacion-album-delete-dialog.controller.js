(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionAlbumDeleteController',PuntuacionAlbumDeleteController);

    PuntuacionAlbumDeleteController.$inject = ['$uibModalInstance', 'entity', 'PuntuacionAlbum'];

    function PuntuacionAlbumDeleteController($uibModalInstance, entity, PuntuacionAlbum) {
        var vm = this;

        vm.puntuacionAlbum = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PuntuacionAlbum.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
