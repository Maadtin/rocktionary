(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionAlbumDetailController', PuntuacionAlbumDetailController);

    PuntuacionAlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PuntuacionAlbum', 'User', 'Album'];

    function PuntuacionAlbumDetailController($scope, $rootScope, $stateParams, previousState, entity, PuntuacionAlbum, User, Album) {
        var vm = this;

        vm.puntuacionAlbum = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:puntuacionAlbumUpdate', function(event, result) {
            vm.puntuacionAlbum = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
