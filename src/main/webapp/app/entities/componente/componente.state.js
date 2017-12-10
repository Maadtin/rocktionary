(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('componente', {
            parent: 'entity',
            url: '/componente',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.componente.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/componente/componentes.html',
                    controller: 'ComponenteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('componente');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('componente-detail', {
            parent: 'componente',
            url: '/componente/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.componente.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/componente/componente-detail.html',
                    controller: 'ComponenteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('componente');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Componente', function($stateParams, Componente) {
                    return Componente.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'componente',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('componente-detail.edit', {
            parent: 'componente-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/componente/componente-dialog.html',
                    controller: 'ComponenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Componente', function(Componente) {
                            return Componente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('componente.new', {
            parent: 'componente',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/componente/componente-dialog.html',
                    controller: 'ComponenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                edad: null,
                                sexo: null,
                                funcionGrupo: null,
                                foto: null,
                                fotoContentType: null,
                                fechaEntrada: null,
                                fechaSalida: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('componente', null, { reload: 'componente' });
                }, function() {
                    $state.go('componente');
                });
            }]
        })
        .state('componente.edit', {
            parent: 'componente',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/componente/componente-dialog.html',
                    controller: 'ComponenteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Componente', function(Componente) {
                            return Componente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('componente', null, { reload: 'componente' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('componente.delete', {
            parent: 'componente',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/componente/componente-delete-dialog.html',
                    controller: 'ComponenteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Componente', function(Componente) {
                            return Componente.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('componente', null, { reload: 'componente' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
