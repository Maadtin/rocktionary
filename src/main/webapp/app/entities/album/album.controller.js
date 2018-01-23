(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('AlbumController', AlbumController);

    AlbumController.$inject = ['album', 'albumTracks', 'AppUtils'];

    function AlbumController(album, albumTracks, AppUtils) {

        var vm = this;


        vm.album = album;
        vm.albumTracks = albumTracks;
        vm.parseMillis = AppUtils.parseMillis;
        vm.validateUrl = AppUtils.validateUrl;
        console.log(album);
        console.log(albumTracks)

        // vm.albums = [];
		  //
        // loadAll();
		  //
        // function loadAll() {
        //     Album.query(function(result) {
        //         vm.albums = result;
        //         vm.searchQuery = null;
        //     });
        // }
    }
})();
