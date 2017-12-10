(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionCancionDialogController', PuntuacionCancionDialogController);

    PuntuacionCancionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PuntuacionCancion', 'User', 'Cancion'];

    function PuntuacionCancionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PuntuacionCancion, User, Cancion) {
        var vm = this;

        vm.puntuacionCancion = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.cancions = Cancion.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.puntuacionCancion.id !== null) {
                PuntuacionCancion.update(vm.puntuacionCancion, onSaveSuccess, onSaveError);
            } else {
                PuntuacionCancion.save(vm.puntuacionCancion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:puntuacionCancionUpdate', result);
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
