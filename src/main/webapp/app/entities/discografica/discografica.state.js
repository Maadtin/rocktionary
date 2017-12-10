(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('discografica', {
            parent: 'entity',
            url: '/discografica',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.discografica.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discografica/discograficas.html',
                    controller: 'DiscograficaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discografica');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('discografica-detail', {
            parent: 'discografica',
            url: '/discografica/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.discografica.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discografica/discografica-detail.html',
                    controller: 'DiscograficaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discografica');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Discografica', function($stateParams, Discografica) {
                    return Discografica.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'discografica',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('discografica-detail.edit', {
            parent: 'discografica-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discografica/discografica-dialog.html',
                    controller: 'DiscograficaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Discografica', function(Discografica) {
                            return Discografica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discografica.new', {
            parent: 'discografica',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discografica/discografica-dialog.html',
                    controller: 'DiscograficaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('discografica', null, { reload: 'discografica' });
                }, function() {
                    $state.go('discografica');
                });
            }]
        })
        .state('discografica.edit', {
            parent: 'discografica',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discografica/discografica-dialog.html',
                    controller: 'DiscograficaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Discografica', function(Discografica) {
                            return Discografica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discografica', null, { reload: 'discografica' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discografica.delete', {
            parent: 'discografica',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discografica/discografica-delete-dialog.html',
                    controller: 'DiscograficaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Discografica', function(Discografica) {
                            return Discografica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('discografica', null, { reload: 'discografica' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
