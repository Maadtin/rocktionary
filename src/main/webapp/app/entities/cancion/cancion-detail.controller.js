(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('CancionDetailController', CancionDetailController);

    CancionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Cancion', 'Album', 'PuntuacionCancion', 'ComentarCancion'];

    function CancionDetailController($scope, $rootScope, $stateParams, previousState, entity, Cancion, Album, PuntuacionCancion, ComentarCancion) {
        var vm = this;

        vm.cancion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:cancionUpdate', function(event, result) {
            vm.cancion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
