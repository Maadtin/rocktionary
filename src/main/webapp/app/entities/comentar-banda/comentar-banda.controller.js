(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarBandaController', ComentarBandaController);

    ComentarBandaController.$inject = ['ComentarBanda'];

    function ComentarBandaController(ComentarBanda) {

        var vm = this;

        vm.comentarBandas = [];

        loadAll();

        function loadAll() {
            ComentarBanda.query(function(result) {
                vm.comentarBandas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
