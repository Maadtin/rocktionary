(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarAlbumDialogController', ComentarAlbumDialogController);

    ComentarAlbumDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ComentarAlbum', 'User', 'Album'];

    function ComentarAlbumDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ComentarAlbum, User, Album) {
        var vm = this;

        vm.comentarAlbum = entity;
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
            if (vm.comentarAlbum.id !== null) {
                ComentarAlbum.update(vm.comentarAlbum, onSaveSuccess, onSaveError);
            } else {
                ComentarAlbum.save(vm.comentarAlbum, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:comentarAlbumUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaComentario = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
