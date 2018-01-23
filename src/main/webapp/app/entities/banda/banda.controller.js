(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaController', BandaController);

    BandaController.$inject = ['DataUtils', 'banda', 'topTracks', 'BandaService', 'AppUtils'];

    function BandaController(DataUtils, banda, topTracks, BandaService, AppUtils) {

        let vm = this;


        vm.validateURL = AppUtils.validateUrl;


        vm.banda = banda;
        vm.topTracks = topTracks;
        console.log(banda);
        if (vm.banda) {
           BandaService.getBandaInfo(vm.banda.name).get()
               .$promise.then(res => vm.bandaBio = res.artist.bio.content)
        }
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
