(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('DiscograficaController', DiscograficaController);

    DiscograficaController.$inject = ['Discografica'];

    function DiscograficaController(Discografica) {

        var vm = this;

        vm.discograficas = [];

        loadAll();

        function loadAll() {
            Discografica.query(function(result) {
                vm.discograficas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
