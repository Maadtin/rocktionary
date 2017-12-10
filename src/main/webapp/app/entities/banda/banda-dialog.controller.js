(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaDialogController', BandaDialogController);

    BandaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Banda', 'Discografica', 'Componente', 'PuntuacionBanda', 'ComentarBanda'];

    function BandaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Banda, Discografica, Componente, PuntuacionBanda, ComentarBanda) {
        var vm = this;

        vm.banda = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.discograficas = Discografica.query();
        vm.componentes = Componente.query();
        vm.puntuacionbandas = PuntuacionBanda.query();
        vm.comentarbandas = ComentarBanda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.banda.id !== null) {
                Banda.update(vm.banda, onSaveSuccess, onSaveError);
            } else {
                Banda.save(vm.banda, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:bandaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datacreacion = false;
        vm.datePickerOpenStatus.anosactivo = false;

        vm.setFoto = function ($file, banda) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        banda.foto = base64Data;
                        banda.fotoContentType = $file.type;
                    });
                });
            }
        };

        vm.setLogo = function ($file, banda) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        banda.logo = base64Data;
                        banda.logoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
