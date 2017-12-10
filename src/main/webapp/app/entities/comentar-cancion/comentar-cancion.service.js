(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('ComentarCancion', ComentarCancion);

    ComentarCancion.$inject = ['$resource', 'DateUtils'];

    function ComentarCancion ($resource, DateUtils) {
        var resourceUrl =  'api/comentar-cancions/:id';

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
