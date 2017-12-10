(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarBandaDialogController', ComentarBandaDialogController);

    ComentarBandaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ComentarBanda', 'User', 'Banda'];

    function ComentarBandaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ComentarBanda, User, Banda) {
        var vm = this;

        vm.comentarBanda = entity;
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
            if (vm.comentarBanda.id !== null) {
                ComentarBanda.update(vm.comentarBanda, onSaveSuccess, onSaveError);
            } else {
                ComentarBanda.save(vm.comentarBanda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:comentarBandaUpdate', result);
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
