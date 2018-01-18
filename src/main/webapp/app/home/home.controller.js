(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'HomeService', '$http'];

    function HomeController ($scope, Principal, LoginService, $state, HomeService, $http) {

        var vm = this;
        vm.placeHolderText = 'albumes';
        vm.type = 'album'
        let timeOut = null;


        // HomeService.getToken().then(res => console.log(res))


        function buscaCancionPorNombre (nombre) {
            vm.inputSearch = nombre;
            vm.handleOnInputChange();
            $scope.$apply()
        }

        function cambiaCategoria (cat) {
            switch (cat) {
                case 'canciones':angular.element('#canciones').trigger('click');break;
                case 'albumes':angular.element('#albumes').trigger('click');break;
                case 'bandas':angular.element('#bandas').trigger('click');break;
            }
        }

        function limpiaResultados () {
            vm.inputSearch = '';
            vm.showResults = false;
            $scope.$apply()
        }

        function adios () {
            if (annyang.isListening()) {
                limpiaResultados()
                annyang.abort()
            }
        }

        let commands = {
            'jarvis busca *val': buscaCancionPorNombre,
            'jarvis cambia a *cat': cambiaCategoria,
            'jarvis limpia la búsqueda': limpiaResultados,
            'jarvis limpia la busqueda': limpiaResultados,
            'adiós jarvis': adios,
            'adios jarvis': adios
        }


        annyang.addCallback('end', function () {
            vm.isListening = false;
            $scope.$apply();
        })

        annyang.addCallback('start', function () {
            vm.isListening = true;
            $scope.$apply()
        })


        vm.triggerMic = () => {
            if (annyang.isListening()) {
                annyang.abort();
                return;
            }
            annyang.setLanguage('es-ES')
            annyang.addCommands(commands)
            annyang.start({ autoRestart: true, continuous: true })
        };


        vm.onEnterKey = $event => {

            if ($event.key === 'Enter') {
                vm.handleOnInputChange()
            }

        }

        vm.handleClassChange = function (filter, e) {
                angular.element(e.target).siblings().removeClass();
                angular.element(e.target).addClass('active')



            vm.placeHolderText = filter;

            switch(vm.placeHolderText) {
                case 'albumes': vm.type = 'album';  break;
                case 'canciones': vm.type = 'track'; break;
                case 'bandas': vm.type='artist'; break;
            }
        }

        let onData = data => {
            vm.isLoading = false;
            if (data.length === 0) {
                vm.notFound = true;
                vm.notFoundMsg = 'No se encontrarón resultados'
            } else {
                vm.notFound = false;
                vm.listaResultados = data;
                console.log(data)
            }
        }

        vm.parseMillis = function(millis) {
            var minutes = Math.floor(millis / 60000);
            var seconds = ((millis % 60000) / 1000).toFixed(0);
            return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
        }

        let onError = err => {
            if (vm.inputSearch !== undefined) {
                vm.showResults = !!vm.inputSearch;
                vm.isLoading = false;
                vm.notFound = true;
                switch (err.status) {
                    case 404:
                        vm.notFoundMsg = '404 no se encontrarón resultados'
                        break;
                    case 401:
                        vm.notFoundMsg = '401 no estás autorizado'
                        break;
                }

            }
        }


        vm.handleOnInputChange = function () {
            vm.showResults = !!vm.inputSearch;
            !vm.showResults && angular.element('.home__card-container').empty()
            vm.showResults ? angular.element('#book').addClass('open') : angular.element('#book').removeClass('open')
            if (vm.inputSearch) {



                let endPoint = `https://api.spotify.com/v1/search?q=${encodeURIComponent(vm.inputSearch)}&type=${vm.type}`;
                vm.isLoading = true;
                clearTimeout(timeOut);
                timeOut = setTimeout ( () => {
                    HomeService
                        .busqueda(endPoint)
                        .get()
                        .$promise
                        .then(onData,onError)
                }, 500)
            }

        };

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        // vm.getSpotifyTrack = function () {
        //     HomeService
        //         .getSpotifyTrack(vm.inputSearch)
        //         .get()
        //         .$promise
        //         .then(res => console.log(res))
        // }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }


    }
})();
