(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaController', BandaController);

    BandaController.$inject = ['DataUtils', 'Banda'];

    function BandaController(DataUtils, Banda) {

        var vm = this;

        vm.bandas = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Banda.query(function(result) {
                vm.bandas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
