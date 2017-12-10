(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('AlbumDialogController', AlbumDialogController);

    AlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Album', 'Discografica', 'PuntuacionAlbum', 'ComentarAlbum', 'Cancion'];

    function AlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Album, Discografica, PuntuacionAlbum, ComentarAlbum, Cancion) {
        var vm = this;

        vm.album = entity;
        vm.clear = clear;
        vm.save = save;
        vm.discograficas = Discografica.query();
        vm.puntuacionalbums = PuntuacionAlbum.query();
        vm.comentaralbums = ComentarAlbum.query();
        vm.cancions = Cancion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.album.id !== null) {
                Album.update(vm.album, onSaveSuccess, onSaveError);
            } else {
                Album.save(vm.album, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:albumUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
