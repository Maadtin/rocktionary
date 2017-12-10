(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaDetailController', BandaDetailController);

    BandaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Banda', 'Discografica', 'Componente', 'PuntuacionBanda', 'ComentarBanda'];

    function BandaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Banda, Discografica, Componente, PuntuacionBanda, ComentarBanda) {
        var vm = this;

        vm.banda = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('rocktionaryApp:bandaUpdate', function(event, result) {
            vm.banda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
