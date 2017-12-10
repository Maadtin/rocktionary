(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('ComentarBanda', ComentarBanda);

    ComentarBanda.$inject = ['$resource', 'DateUtils'];

    function ComentarBanda ($resource, DateUtils) {
        var resourceUrl =  'api/comentar-bandas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaComentario = DateUtils.convertDateTimeFromServer(data.fechaComentario);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
