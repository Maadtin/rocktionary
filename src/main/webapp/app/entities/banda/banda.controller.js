(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaController', BandaController);

    BandaController.$inject = ['DataUtils', 'banda'];

    function BandaController(DataUtils, banda) {


        var vm = this;
        //
        // vm.bandas = [];
        // vm.openFile = DataUtils.openFile;
        // vm.byteSize = DataUtils.byteSize;
        //
        vm.banda = banda;
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
