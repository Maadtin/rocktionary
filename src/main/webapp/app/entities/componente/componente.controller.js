(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('ComponenteController', ComponenteController);

    ComponenteController.$inject = ['DataUtils', 'Componente'];

    function ComponenteController(DataUtils, Componente) {

        var vm = this;

        vm.componentes = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Componente.query(function(result) {
                vm.componentes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
