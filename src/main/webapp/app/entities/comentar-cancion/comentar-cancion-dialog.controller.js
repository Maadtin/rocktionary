(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarCancionDialogController', ComentarCancionDialogController);

    ComentarCancionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ComentarCancion', 'User', 'Cancion'];

    function ComentarCancionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ComentarCancion, User, Cancion) {
        var vm = this;

        vm.comentarCancion = entity;
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
            if (vm.comentarCancion.id !== null) {
                ComentarCancion.update(vm.comentarCancion, onSaveSuccess, onSaveError);
            } else {
                ComentarCancion.save(vm.comentarCancion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:comentarCancionUpdate', result);
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
