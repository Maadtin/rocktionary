(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('PuntuacionAlbum', PuntuacionAlbum);

    PuntuacionAlbum.$inject = ['$resource', 'DateUtils'];

    function PuntuacionAlbum ($resource, DateUtils) {
        var resourceUrl =  'api/puntuacion-albums/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaPuntuacion = DateUtils.convertDateTimeFromServer(data.fechaPuntuacion);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
