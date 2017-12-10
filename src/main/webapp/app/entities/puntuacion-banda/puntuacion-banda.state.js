(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('puntuacion-banda', {
            parent: 'entity',
            url: '/puntuacion-banda',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.puntuacionBanda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-banda/puntuacion-bandas.html',
                    controller: 'PuntuacionBandaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionBanda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('puntuacion-banda-detail', {
            parent: 'puntuacion-banda',
            url: '/puntuacion-banda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.puntuacionBanda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-banda/puntuacion-banda-detail.html',
                    controller: 'PuntuacionBandaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionBanda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PuntuacionBanda', function($stateParams, PuntuacionBanda) {
                    return PuntuacionBanda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'puntuacion-banda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('puntuacion-banda-detail.edit', {
            parent: 'puntuacion-banda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-banda/puntuacion-banda-dialog.html',
                    controller: 'PuntuacionBandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionBanda', function(PuntuacionBanda) {
                            return PuntuacionBanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-banda.new', {
            parent: 'puntuacion-banda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-banda/puntuacion-banda-dialog.html',
                    controller: 'PuntuacionBandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                valoracion: null,
                                fechaPuntuacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('puntuacion-banda', null, { reload: 'puntuacion-banda' });
                }, function() {
                    $state.go('puntuacion-banda');
                });
            }]
        })
        .state('puntuacion-banda.edit', {
            parent: 'puntuacion-banda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-banda/puntuacion-banda-dialog.html',
                    controller: 'PuntuacionBandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionBanda', function(PuntuacionBanda) {
                            return PuntuacionBanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-banda', null, { reload: 'puntuacion-banda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-banda.delete', {
            parent: 'puntuacion-banda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-banda/puntuacion-banda-delete-dialog.html',
                    controller: 'PuntuacionBandaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PuntuacionBanda', function(PuntuacionBanda) {
                            return PuntuacionBanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-banda', null, { reload: 'puntuacion-banda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
