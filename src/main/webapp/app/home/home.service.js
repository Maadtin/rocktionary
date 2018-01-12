angular
    .module('rocktionaryApp')
    .service('HomeService', HomeService)


HomeService.$inject = ['$resource', '$localStorage', '$sessionStorage', '$http'];

function HomeService ($resource, $localStorage, $sessionStorage, $http) {

    // this.getToken = function () {
    //     return $http({
    //         method: 'POST',
    //         url: 'https://accounts.spotify.com/api/token',
    //         headers: {
    //             'Authorization': 'Basic ODk2NzZmNGUxNDQ3NDU4NGE4MWE4YzI4MDgwMWVlNzc6MDcxOTEwYmVhODA1NDk2N2JmNzY1NmQyYTA1YmJkNTY=',
    //             'Content-Type': 'application/x-www-form-urlencoded'
    //         },
    //         transformRequest: function(obj) {
    //             var str = [];
    //             for(var p in obj)
    //                 str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
    //             return str.join("&");
    //         },
    //         data: {grant_type: "client_credentials"}
    //     })
    // }


    this.busqueda = function (url) {
        //var token = $localStorage.authenticationToken || $sessionStorage.authenticationToken;
        var headers = {'Authorization': 'Bearer BQDUSL5eb7eBEeywxvTTYy6WdO6w1HbJvFc-Xe3GSwxpIbk5rhGoSEoeih1CyFCfI4RcXkRI77UlzSmCj6A'}
        return $resource(url, null, {
            query: {
                method: 'GET',
                isArray: true,
                headers
            },
            get: {
                method: 'GET',
                isArray: false,
                headers
            }
        })
    }

    // this.getSpotifyTrack = function (track) {
    //     return $resource(`https://api.spotify.com/v1/search?q=${track}&type=track`, null, {
    //         query: {
    //             method: 'GET',
    //             isArray: true
    //         },
    //         get: {
    //             method: 'GET',
    //             headers: {'Authorization': 'Bearer BQBUE7F292X8qzX4V-4G5YjNpME0pDigoHHk4Btz-4HWNQSLEq-bvThbbSC76hMcITlSt1taajP3DWgDyMKTsRUyL7Jmk4RNdJFJukn-3oW0euiUNP73VYmNaRyzFEmJWVamNtsmiIDhdXpychg6HZkFLzG7ZDksR8xSD6hVsKV60c__XAUqNutRkXzolTh74BUzZfYZO7otrzpJs1NrFzJe5h_xFXvX0dspS0P-m6VcJ6u6KeLGW8MOAhPS9P4tcyLehnk'}
    //         }
    //     })
    // }


}
