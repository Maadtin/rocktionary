(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('UserFollowingUserDeleteController',UserFollowingUserDeleteController);

    UserFollowingUserDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserFollowingUser'];

    function UserFollowingUserDeleteController($uibModalInstance, entity, UserFollowingUser) {
        var vm = this;

        vm.userFollowingUser = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserFollowingUser.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
