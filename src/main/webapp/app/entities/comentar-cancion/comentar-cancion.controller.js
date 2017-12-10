(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComentarCancionController', ComentarCancionController);

    ComentarCancionController.$inject = ['ComentarCancion'];

    function ComentarCancionController(ComentarCancion) {

        var vm = this;

        vm.comentarCancions = [];

        loadAll();

        function loadAll() {
            ComentarCancion.query(function(result) {
                vm.comentarCancions = result;
                vm.searchQuery = null;
            });
        }
    }
})();
