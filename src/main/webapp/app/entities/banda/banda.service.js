// (function() {
//     'use strict';
//     angular
//         .module('rocktionaryApp')
//         .factory('Banda', Banda);
//
//     Banda.$inject = ['$resource', 'DateUtils'];
//
//     function Banda ($resource, DateUtils) {
//         var resourceUrl =  'api/bandas/:id';
//
//         return $resource(resourceUrl, {}, {
//             'query': { method: 'GET', isArray: true},
//             'get': {
//                 method: 'GET',
//                 transformResponse: function (data) {
//                     if (data) {
//                         data = angular.fromJson(data);
//                         data.datacreacion = DateUtils.convertLocalDateFromServer(data.datacreacion);
//                         data.anosactivo = DateUtils.convertLocalDateFromServer(data.anosactivo);
//                     }
//                     return data;
//                 }
//             },
//             'update': {
//                 method: 'PUT',
//                 transformRequest: function (data) {
//                     var copy = angular.copy(data);
//                     copy.datacreacion = DateUtils.convertLocalDateToServer(copy.datacreacion);
//                     copy.anosactivo = DateUtils.convertLocalDateToServer(copy.anosactivo);
//                     return angular.toJson(copy);
//                 }
//             },
//             'save': {
//                 method: 'POST',
//                 transformRequest: function (data) {
//                     var copy = angular.copy(data);
//                     copy.datacreacion = DateUtils.convertLocalDateToServer(copy.datacreacion);
//                     copy.anosactivo = DateUtils.convertLocalDateToServer(copy.anosactivo);
//                     return angular.toJson(copy);
//                 }
//             }
//         });
//     }
// })();

angular
    .module('rocktionaryApp')
    .service('BandaService', ['$resource', function ($resource) {

        let base = 'https://api.spotify.com';
        let token = window.spotifyToken;
        this.getBanda = function () {
            return $resource(`${base}/v1/artists/:id`, {}, {
                'get': {
                    method: 'GET',
                    headers: {
                        'Authorization': token
                    }
                }
            })
        };

        this.getBandaInfo = function (inputSearch) {
            return $resource(`http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&api_key=10ceb7a9cb40ae1b5c0b517bc625c8f5&artist=${inputSearch}&format=json`)
        };

        this.getTopTracks = function () {
            return $resource(`${base}/v1/artists/:id/top-tracks?country=US`, {}, {
                'get': {
                    method: 'GET',
                    headers: {
                        'Authorization': token
                    }
                }
            })
        };

        this.getBandaVideoTrack = function (trackName) {
            return $resource(`https://www.googleapis.com/youtube/v3/search?part=snippet&q=${trackName}&maxResults=1&key=AIzaSyBh4jKVZPAs4VFdpr2RAdPa_3bHFVRjQXQ&type=video`)
        }
    }]);
