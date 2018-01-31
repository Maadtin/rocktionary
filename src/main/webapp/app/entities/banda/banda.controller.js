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

        vm.toggleIcon = function ($e) {
            angular.element($e.target).toggleClass('fa-pause');
            angular.element('.track-icon-play').not($e.target).removeClass('fa-pause').addClass('fa-play')

            BandaService.getBandaVideoTrack($e.target.dataset.trackName).get()
                .$promise
                .then(res => {
                    const { videoId } = res.items[0].id;
                    angular.element('#videoPlayer').html('').html(
                        `<iframe width="560" height="315" src="https://www.youtube.com/embed/${videoId}?rel=0&autoplay=1" frameborder="0"    allowfullscreen></iframe>`
                    ).css('display', 'block');
                })
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
