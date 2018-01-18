(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaController', BandaController);

    BandaController.$inject = ['DataUtils', 'banda', 'topTracks', '$sce'];

    function BandaController(DataUtils, banda, topTracks, $sce) {

        let vm = this;

        vm.validateURL = function (url) {
            return $sce.trustAsResourceUrl(url);
        }


        vm.banda = banda;
        vm.topTracks = topTracks;
        console.log(topTracks)
        //
        // vm.bandas = [];
        // vm.openFile = DataUtils.openFile;
        // vm.byteSize = DataUtils.byteSize;
        //
        //
        // loadAll();
        //
        // function loadAll() {
        //     Banda.query(function(result) {
        //         vm.bandas = result;
        //         vm.searchQuery = null;
        //     });
        // }
    }
})();
