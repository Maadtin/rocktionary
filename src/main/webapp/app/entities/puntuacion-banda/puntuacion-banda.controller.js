(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionBandaController', PuntuacionBandaController);

    PuntuacionBandaController.$inject = ['PuntuacionBanda'];

    function PuntuacionBandaController(PuntuacionBanda) {

        var vm = this;

        vm.puntuacionBandas = [];

        loadAll();

        function loadAll() {
            PuntuacionBanda.query(function(result) {
                vm.puntuacionBandas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
