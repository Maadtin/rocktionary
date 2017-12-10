(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('puntuacion-cancion', {
            parent: 'entity',
            url: '/puntuacion-cancion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.puntuacionCancion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-cancion/puntuacion-cancions.html',
                    controller: 'PuntuacionCancionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionCancion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('puntuacion-cancion-detail', {
            parent: 'puntuacion-cancion',
            url: '/puntuacion-cancion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.puntuacionCancion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-cancion/puntuacion-cancion-detail.html',
                    controller: 'PuntuacionCancionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionCancion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PuntuacionCancion', function($stateParams, PuntuacionCancion) {
                    return PuntuacionCancion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'puntuacion-cancion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('puntuacion-cancion-detail.edit', {
            parent: 'puntuacion-cancion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-cancion/puntuacion-cancion-dialog.html',
                    controller: 'PuntuacionCancionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionCancion', function(PuntuacionCancion) {
                            return PuntuacionCancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-cancion.new', {
            parent: 'puntuacion-cancion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-cancion/puntuacion-cancion-dialog.html',
                    controller: 'PuntuacionCancionDialogController',
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
                    $state.go('puntuacion-cancion', null, { reload: 'puntuacion-cancion' });
                }, function() {
                    $state.go('puntuacion-cancion');
                });
            }]
        })
        .state('puntuacion-cancion.edit', {
            parent: 'puntuacion-cancion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-cancion/puntuacion-cancion-dialog.html',
                    controller: 'PuntuacionCancionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionCancion', function(PuntuacionCancion) {
                            return PuntuacionCancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-cancion', null, { reload: 'puntuacion-cancion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-cancion.delete', {
            parent: 'puntuacion-cancion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-cancion/puntuacion-cancion-delete-dialog.html',
                    controller: 'PuntuacionCancionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PuntuacionCancion', function(PuntuacionCancion) {
                            return PuntuacionCancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-cancion', null, { reload: 'puntuacion-cancion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
