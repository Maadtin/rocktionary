(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionCancionDetailController', PuntuacionCancionDetailController);

    PuntuacionCancionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PuntuacionCancion', 'User', 'Cancion'];

    function PuntuacionCancionDetailController($scope, $rootScope, $stateParams, previousState, entity, PuntuacionCancion, User, Cancion) {
        var vm = this;

        vm.puntuacionCancion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:puntuacionCancionUpdate', function(event, result) {
            vm.puntuacionCancion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
