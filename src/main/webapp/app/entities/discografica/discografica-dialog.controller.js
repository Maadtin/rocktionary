(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('DiscograficaDialogController', DiscograficaDialogController);

    DiscograficaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Discografica', 'Album', 'Banda'];

    function DiscograficaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Discografica, Album, Banda) {
        var vm = this;

        vm.discografica = entity;
        vm.clear = clear;
        vm.save = save;
        vm.albums = Album.query();
        vm.bandas = Banda.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.discografica.id !== null) {
                Discografica.update(vm.discografica, onSaveSuccess, onSaveError);
            } else {
                Discografica.save(vm.discografica, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:discograficaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
