(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('CancionController', CancionController);

    CancionController.$inject = ['cancion'];

    function CancionController(cancion) {

        var vm = this;

        vm.cancion = cancion;

        console.log(cancion)

        // vm.cancions = [];
		  //
        // loadAll();
		  //
        // function loadAll() {
        //     Cancion.query(function(result) {
        //         vm.cancions = result;
        //         vm.searchQuery = null;
        //     });
        // }
    }
})();
