(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionAlbumController', PuntuacionAlbumController);

    PuntuacionAlbumController.$inject = ['PuntuacionAlbum'];

    function PuntuacionAlbumController(PuntuacionAlbum) {

        var vm = this;

        vm.puntuacionAlbums = [];

        loadAll();

        function loadAll() {
            PuntuacionAlbum.query(function(result) {
                vm.puntuacionAlbums = result;
                vm.searchQuery = null;
            });
        }
    }
})();
