(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarAlbumDetailController', ComentarAlbumDetailController);

    ComentarAlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ComentarAlbum', 'User', 'Album'];

    function ComentarAlbumDetailController($scope, $rootScope, $stateParams, previousState, entity, ComentarAlbum, User, Album) {
        var vm = this;

        vm.comentarAlbum = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:comentarAlbumUpdate', function(event, result) {
            vm.comentarAlbum = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
