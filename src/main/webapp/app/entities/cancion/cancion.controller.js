(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('CancionController', CancionController);

    CancionController.$inject = ['Cancion'];

    function CancionController(Cancion) {

        var vm = this;

        vm.cancions = [];

        loadAll();

        function loadAll() {
            Cancion.query(function(result) {
                vm.cancions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
