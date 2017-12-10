(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('PuntuacionBanda', PuntuacionBanda);

    PuntuacionBanda.$inject = ['$resource', 'DateUtils'];

    function PuntuacionBanda ($resource, DateUtils) {
        var resourceUrl =  'api/puntuacion-bandas/:id';

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
