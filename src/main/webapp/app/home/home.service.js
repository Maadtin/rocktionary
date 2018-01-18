angular
    .module('rocktionaryApp')
    .service('HomeService', HomeService)


HomeService.$inject = ['$resource', '$localStorage', '$sessionStorage'];

function HomeService ($resource, $localStorage, $sessionStorage) {

    this.busqueda = function (url) {
        //var token = $localStorage.authenticationToken || $sessionStorage.authenticationToken;
        let token = window.spotifyToken;
        let headers = {'Authorization': token};
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
