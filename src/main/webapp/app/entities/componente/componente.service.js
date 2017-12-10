(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('Componente', Componente);

    Componente.$inject = ['$resource', 'DateUtils'];

    function Componente ($resource, DateUtils) {
        var resourceUrl =  'api/componentes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaEntrada = DateUtils.convertLocalDateFromServer(data.fechaEntrada);
                        data.fechaSalida = DateUtils.convertLocalDateFromServer(data.fechaSalida);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaEntrada = DateUtils.convertLocalDateToServer(copy.fechaEntrada);
                    copy.fechaSalida = DateUtils.convertLocalDateToServer(copy.fechaSalida);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fechaEntrada = DateUtils.convertLocalDateToServer(copy.fechaEntrada);
                    copy.fechaSalida = DateUtils.convertLocalDateToServer(copy.fechaSalida);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
