(function() {
    'use strict';
    angular
        .module('rocktionaryApp')
        .factory('UserFollowingUser', UserFollowingUser);

    UserFollowingUser.$inject = ['$resource', 'DateUtils'];

    function UserFollowingUser ($resource, DateUtils) {
        var resourceUrl =  'api/user-following-users/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.since = DateUtils.convertDateTimeFromServer(data.since);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
