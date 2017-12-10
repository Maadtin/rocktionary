(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('AlbumDetailController', AlbumDetailController);

    AlbumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Album', 'Discografica', 'PuntuacionAlbum', 'ComentarAlbum', 'Cancion'];

    function AlbumDetailController($scope, $rootScope, $stateParams, previousState, entity, Album, Discografica, PuntuacionAlbum, ComentarAlbum, Cancion) {
        var vm = this;

        vm.album = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:albumUpdate', function(event, result) {
            vm.album = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
