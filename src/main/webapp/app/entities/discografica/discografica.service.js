(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('Discografica', Discografica);

    Discografica.$inject = ['$resource'];

    function Discografica ($resource) {
        var resourceUrl =  'api/discograficas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
