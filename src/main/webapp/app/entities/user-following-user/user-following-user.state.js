(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-following-user', {
            parent: 'entity',
            url: '/user-following-user',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.userFollowingUser.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-following-user/user-following-users.html',
                    controller: 'UserFollowingUserController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userFollowingUser');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-following-user-detail', {
            parent: 'user-following-user',
            url: '/user-following-user/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.userFollowingUser.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-following-user/user-following-user-detail.html',
                    controller: 'UserFollowingUserDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userFollowingUser');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserFollowingUser', function($stateParams, UserFollowingUser) {
                    return UserFollowingUser.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-following-user',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-following-user-detail.edit', {
            parent: 'user-following-user-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-following-user/user-following-user-dialog.html',
                    controller: 'UserFollowingUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserFollowingUser', function(UserFollowingUser) {
                            return UserFollowingUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-following-user.new', {
            parent: 'user-following-user',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-following-user/user-following-user-dialog.html',
                    controller: 'UserFollowingUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                since: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-following-user', null, { reload: 'user-following-user' });
                }, function() {
                    $state.go('user-following-user');
                });
            }]
        })
        .state('user-following-user.edit', {
            parent: 'user-following-user',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-following-user/user-following-user-dialog.html',
                    controller: 'UserFollowingUserDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserFollowingUser', function(UserFollowingUser) {
                            return UserFollowingUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-following-user', null, { reload: 'user-following-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-following-user.delete', {
            parent: 'user-following-user',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-following-user/user-following-user-delete-dialog.html',
                    controller: 'UserFollowingUserDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserFollowingUser', function(UserFollowingUser) {
                            return UserFollowingUser.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-following-user', null, { reload: 'user-following-user' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
