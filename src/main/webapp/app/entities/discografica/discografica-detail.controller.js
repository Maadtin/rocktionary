(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('DiscograficaDetailController', DiscograficaDetailController);

    DiscograficaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Discografica', 'Album', 'Banda'];

    function DiscograficaDetailController($scope, $rootScope, $stateParams, previousState, entity, Discografica, Album, Banda) {
        var vm = this;

        vm.discografica = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:discograficaUpdate', function(event, result) {
            vm.discografica = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
