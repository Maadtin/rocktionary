(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('PuntuacionCancion', PuntuacionCancion);

    PuntuacionCancion.$inject = ['$resource', 'DateUtils'];

    function PuntuacionCancion ($resource, DateUtils) {
        var resourceUrl =  'api/puntuacion-cancions/:id';

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
