(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarBandaDetailController', ComentarBandaDetailController);

    ComentarBandaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ComentarBanda', 'User', 'Banda'];

    function ComentarBandaDetailController($scope, $rootScope, $stateParams, previousState, entity, ComentarBanda, User, Banda) {
        var vm = this;

        vm.comentarBanda = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:comentarBandaUpdate', function(event, result) {
            vm.comentarBanda = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
