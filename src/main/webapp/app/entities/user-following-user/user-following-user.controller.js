(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('UserFollowingUserController', UserFollowingUserController);

    UserFollowingUserController.$inject = ['UserFollowingUser'];

    function UserFollowingUserController(UserFollowingUser) {

        var vm = this;

        vm.userFollowingUsers = [];

        loadAll();

        function loadAll() {
            UserFollowingUser.query(function(result) {
                vm.userFollowingUsers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
