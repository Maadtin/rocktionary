(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComponenteDetailController', ComponenteDetailController);

    ComponenteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Componente', 'Banda'];

    function ComponenteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Componente, Banda) {
        var vm = this;

        vm.componente = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('rocktionaryApp:componenteUpdate', function(event, result) {
            vm.componente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
