(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionBandaDetailController', PuntuacionBandaDetailController);

    PuntuacionBandaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PuntuacionBanda', 'User', 'Banda'];

    function PuntuacionBandaDetailController($scope, $rootScope, $stateParams, previousState, entity, PuntuacionBanda, User, Banda) {
        var vm = this;

        vm.puntuacionBanda = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:puntuacionBandaUpdate', function(event, result) {
            vm.puntuacionBanda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
