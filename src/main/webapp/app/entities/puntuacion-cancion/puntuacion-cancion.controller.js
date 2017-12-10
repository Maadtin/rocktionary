(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('PuntuacionCancionController', PuntuacionCancionController);

    PuntuacionCancionController.$inject = ['PuntuacionCancion'];

    function PuntuacionCancionController(PuntuacionCancion) {

        var vm = this;

        vm.puntuacionCancions = [];

        loadAll();

        function loadAll() {
            PuntuacionCancion.query(function(result) {
                vm.puntuacionCancions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
