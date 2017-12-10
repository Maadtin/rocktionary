(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('Banda', Banda);

    Banda.$inject = ['$resource', 'DateUtils'];

    function Banda ($resource, DateUtils) {
        var resourceUrl =  'api/bandas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datacreacion = DateUtils.convertLocalDateFromServer(data.datacreacion);
                        data.anosactivo = DateUtils.convertLocalDateFromServer(data.anosactivo);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datacreacion = DateUtils.convertLocalDateToServer(copy.datacreacion);
                    copy.anosactivo = DateUtils.convertLocalDateToServer(copy.anosactivo);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datacreacion = DateUtils.convertLocalDateToServer(copy.datacreacion);
                    copy.anosactivo = DateUtils.convertLocalDateToServer(copy.anosactivo);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
