(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComponenteDialogController', ComponenteDialogController);

    ComponenteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Componente', 'Banda'];

    function ComponenteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Componente, Banda) {
        var vm = this;

        vm.componente = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.bandas = Banda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.componente.id !== null) {
                Componente.update(vm.componente, onSaveSuccess, onSaveError);
            } else {
                Componente.save(vm.componente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:componenteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, componente) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        componente.foto = base64Data;
                        componente.fotoContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.fechaEntrada = false;
        vm.datePickerOpenStatus.fechaSalida = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
