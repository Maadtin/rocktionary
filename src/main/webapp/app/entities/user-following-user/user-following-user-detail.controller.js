(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('UserFollowingUserDetailController', UserFollowingUserDetailController);

    UserFollowingUserDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserFollowingUser', 'User'];

    function UserFollowingUserDetailController($scope, $rootScope, $stateParams, previousState, entity, UserFollowingUser, User) {
        var vm = this;

        vm.userFollowingUser = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rocktionaryApp:userFollowingUserUpdate', function(event, result) {
            vm.userFollowingUser = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
