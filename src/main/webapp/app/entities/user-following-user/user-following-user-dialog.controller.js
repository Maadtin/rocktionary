(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .controller('UserFollowingUserDialogController', UserFollowingUserDialogController);

    UserFollowingUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserFollowingUser', 'User'];

    function UserFollowingUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserFollowingUser, User) {
        var vm = this;

        vm.userFollowingUser = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userFollowingUser.id !== null) {
                UserFollowingUser.update(vm.userFollowingUser, onSaveSuccess, onSaveError);
            } else {
                UserFollowingUser.save(vm.userFollowingUser, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rocktionaryApp:userFollowingUserUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.since = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
