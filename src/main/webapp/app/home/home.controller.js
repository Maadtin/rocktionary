(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'HomeService'];

    function HomeController ($scope, Principal, LoginService, $state, HomeService) {

        var vm = this;

        ///
        vm.placeHolderText = 'albumes';
        let timeOut = null;
        let recognition = new webkitSpeechRecognition();
        vm.isListening = false;

        recognition.addEventListener('start', function () {

            vm.isListening = true;
            $scope.$apply()
        })

        recognition.addEventListener('end', function () {

            vm.isListening = false
            $scope.$apply()
        })


        vm.triggerMic = () => {

            recognition.continuous = true;
            recognition.interimResults = true;
            recognition.lang = 'es-ES';

            if (vm.isListening) {
                recognition.stop()
                return;
            }
            recognition.start()

        }

        recognition.addEventListener('result', function (e) {

            vm.inputSearch = [...e.results].map(list => list[0]).map(list => list.transcript).join('');

            if (e.results[0].isFinal) {
                vm.handleOnInputChange()
                vm.isListening && recognition.stop()
            }

            $scope.$apply()
        });

        //recognition.addEventListener('end', recognition.start);

        vm.onEnterKey = $event => {

            if ($event.key === 'Enter') {
                vm.handleOnInputChange()
            }

        }

        vm.handleClassChange = function (filter, e) {
                angular.element(e.target).siblings().removeClass();
                angular.element(e.target).addClass('active')

            vm.placeHolderText = filter;
        }

        let onData = data => {
            vm.isLoading = false;
            if (data.length === 0) {
                vm.notFound = true;
                vm.notFoundMsg = 'No se encontrarón resultados'
            } else {
                vm.notFound = false;
                vm.listaResultados = data;
            }
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
            vm.showResults ? angular.element('#book').addClass('open') : angular.element('#book').removeClass('open')
            if (vm.inputSearch) {

                let endPoint = `http://localhost:8080/api/get-${vm.placeHolderText}-by-nombre`;
                vm.isLoading = true;
                clearTimeout(timeOut);
                timeOut = setTimeout ( () => {
                    HomeService
                        .busqueda(endPoint)
                        .query({input: vm.inputSearch})
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
