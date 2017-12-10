(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('ComentarAlbum', ComentarAlbum);

    ComentarAlbum.$inject = ['$resource', 'DateUtils'];

    function ComentarAlbum ($resource, DateUtils) {
        var resourceUrl =  'api/comentar-albums/:id';

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
