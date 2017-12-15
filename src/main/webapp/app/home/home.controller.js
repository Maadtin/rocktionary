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
            vm.listaResultados = data;
        }

        let onError = err => {
            console.log(err)
        }

        vm.handleOnInputChange = function () {
            vm.showResults = !!vm.inputSearch;
            let endPoint = `http://localhost:8080/api/get-${vm.placeHolderText}-by-nombre`;
            vm.isLoading = true;
            clearTimeout(timeOut)
            timeOut = setTimeout ( () => {
                HomeService
                    .busqueda(endPoint)
                    .query({input: vm.inputSearch})
                    .$promise
                    .then(onData,onError)
            }, 500)

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
