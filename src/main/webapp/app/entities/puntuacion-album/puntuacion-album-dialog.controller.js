(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionAlbumDialogController', PuntuacionAlbumDialogController);

    PuntuacionAlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PuntuacionAlbum', 'User', 'Album'];

    function PuntuacionAlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PuntuacionAlbum, User, Album) {
        var vm = this;

        vm.puntuacionAlbum = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.albums = Album.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.puntuacionAlbum.id !== null) {
                PuntuacionAlbum.update(vm.puntuacionAlbum, onSaveSuccess, onSaveError);
            } else {
                PuntuacionAlbum.save(vm.puntuacionAlbum, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:puntuacionAlbumUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaPuntuacion = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
