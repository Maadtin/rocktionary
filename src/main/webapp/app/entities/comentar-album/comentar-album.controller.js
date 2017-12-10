(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarAlbumController', ComentarAlbumController);

    ComentarAlbumController.$inject = ['ComentarAlbum'];

    function ComentarAlbumController(ComentarAlbum) {

        var vm = this;

        vm.comentarAlbums = [];

        loadAll();

        function loadAll() {
            ComentarAlbum.query(function(result) {
                vm.comentarAlbums = result;
                vm.searchQuery = null;
            });
        }
    }
})();
