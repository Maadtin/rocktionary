(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarCancionDetailController', ComentarCancionDetailController);

    ComentarCancionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ComentarCancion', 'User', 'Cancion'];

    function ComentarCancionDetailController($scope, $rootScope, $stateParams, previousState, entity, ComentarCancion, User, Cancion) {
        var vm = this;

        vm.comentarCancion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:comentarCancionUpdate', function(event, result) {
            vm.comentarCancion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
