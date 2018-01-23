// (function() {
//     'use strict';
//     angular
//         .module('rocktionaryApp')
//         .factory('Cancion', Cancion);
//
//     Cancion.$inject = ['$resource'];
//
//     function Cancion ($resource) {
//         var resourceUrl =  'api/cancions/:id';
//
//         return $resource(resourceUrl, {}, {
//             'query': { method: 'GET', isArray: true},
//             'get': {
//                 method: 'GET',
//                 transformResponse: function (data) {
//                     if (data) {
//                         data = angular.fromJson(data);
//                     }
//                     return data;
//                 }
//             },
//             'update': { method:'PUT' }
//         });
//     }
// })();


angular
    .module('rocktionaryApp')
    .service('CancionService', ['$resource', function ($resource) {

        let base = 'https://api.spotify.com';
        let token = window.spotifyToken;

        this.getCancion = function () {
            return $resource(`${base}/v1/tracks/:id`, {}, {
                'get': {
                    method: 'GET',
                    headers: {
                        'Authorization': token
                    }
                }
            })
        }

    }]);
