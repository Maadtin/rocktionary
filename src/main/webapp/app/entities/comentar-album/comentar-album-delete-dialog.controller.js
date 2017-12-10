(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarAlbumDeleteController',ComentarAlbumDeleteController);

    ComentarAlbumDeleteController.$inject = ['$uibModalInstance', 'entity', 'ComentarAlbum'];

    function ComentarAlbumDeleteController($uibModalInstance, entity, ComentarAlbum) {
        var vm = this;

        vm.comentarAlbum = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ComentarAlbum.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
