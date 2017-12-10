(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionBandaDialogController', PuntuacionBandaDialogController);

    PuntuacionBandaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PuntuacionBanda', 'User', 'Banda'];

    function PuntuacionBandaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PuntuacionBanda, User, Banda) {
        var vm = this;

        vm.puntuacionBanda = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.bandas = Banda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.puntuacionBanda.id !== null) {
                PuntuacionBanda.update(vm.puntuacionBanda, onSaveSuccess, onSaveError);
            } else {
                PuntuacionBanda.save(vm.puntuacionBanda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:puntuacionBandaUpdate', result);
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
