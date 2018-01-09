(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('BandaDetailController', BandaDetailController);

    BandaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Banda', 'Discografica', 'Componente', 'PuntuacionBanda', 'ComentarBanda'];

    function BandaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Banda, Discografica, Componente, PuntuacionBanda, ComentarBanda) {
        var vm = this;

        vm.banda = entity;
        //vm.comments = comments[0];
        console.log(this);
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('rocktionaryApp:bandaUpdate', function(event, result) {
            vm.banda = result;
        });
        $scope.$on('$destroy', unsubscribe);


        vm.showGeneral = true;
        vm.triggerClass = function ($e) {
            angular.element($e.target).siblings().removeClass('active')
            angular.element($e.target).addClass('active');

            switch ($e.target.textContent) {
                case 'General': vm.showGeneral = true;vm.showComentarios=false;vm.showSeguidores=false;break;
                case 'Seguidores': vm.showSeguidores = true;vm.showGeneral=false;vm.showComentarios=false;break;
                case 'Comentarios': vm.showComentarios = true;vm.showGeneral=false;vm.showSeguidores=false;break;
            }
        };

        vm.addComment = () => {
            alert()
        }

    }
})();
