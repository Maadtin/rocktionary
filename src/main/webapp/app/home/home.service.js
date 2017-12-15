angular
    .module('rocktionaryApp')
    .service('HomeService', HomeService)


HomeService.$inject = ['$resource', '$localStorage', '$sessionStorage'];

function HomeService ($resource, $localStorage, $sessionStorage) {


    this.busqueda = function (url) {
        var token = $localStorage.authenticationToken || $sessionStorage.authenticationToken;
        var headers = {'Authorization': 'Bearer ' + token}
        return $resource(`${url}/:input`, null, {
            query: {
                method: 'GET',
                isArray: true,
                headers
            }
        })
    }


}
