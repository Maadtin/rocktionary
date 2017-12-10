(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('CancionDialogController', CancionDialogController);

    CancionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Cancion', 'Album', 'PuntuacionCancion', 'ComentarCancion'];

    function CancionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Cancion, Album, PuntuacionCancion, ComentarCancion) {
        var vm = this;

        vm.cancion = entity;
        vm.clear = clear;
        vm.save = save;
        vm.albums = Album.query();
        vm.puntuacioncancions = PuntuacionCancion.query();
        vm.comentarcancions = ComentarCancion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cancion.id !== null) {
                Cancion.update(vm.cancion, onSaveSuccess, onSaveError);
            } else {
                Cancion.save(vm.cancion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:cancionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
