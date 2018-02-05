// (function() {
//     'use strict';
//     angular
//         .module('rocktionaryApp')
//         .factory('Album', Album);
//
//     Album.$inject = ['$resource'];
//
//     function Album ($resource) {
//         var resourceUrl =  'api/albums/:id';<<
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
    .service('AlbumService', ['$resource', function ($resource) {

        let base = 'https://api.spotify.com';
        let token = window.spotifyToken;
        this.getAlbum = function () {
            return $resource(`${base}/v1/albums/:id`, {}, {
                'get': {
                    method: 'GET',
                    headers: {
                        'Authorization': token
                    }
                }
            })
        }

        this.getBandaInfo = function (inputSearch) {
            return $resource(`http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&api_key=10ceb7a9cb40ae1b5c0b517bc625c8f5&artist=${inputSearch}&format=json`)
        };

        this.getAlbumTracks = function () {
            return $resource(`${base}/v1/albums/:id/tracks`, {}, {
                'get': {
                    method: 'GET',
                    headers: {
                        'Authorization': token
                    }
                }
            })
        }

        this.getTopTracks = function () {
            return $resource(`${base}/v1/artists/:id/top-tracks?country=US`, {}, {
                'get': {
                    method: 'GET',
                    headers: {
                        'Authorization': token
                    }
                }
            })
        }


    }]);
