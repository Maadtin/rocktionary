(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('banda', {
            parent: 'entity',
            url: '/banda',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.banda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/banda/bandas.html',
                    controller: 'BandaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('banda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('banda-detail', {
            parent: 'banda',
            url: '/banda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.banda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/banda/banda-detail.html',
                    controller: 'BandaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('banda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Banda', function($stateParams, Banda) {
                    return Banda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'banda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('banda-detail.edit', {
            parent: 'banda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/banda/banda-dialog.html',
                    controller: 'BandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Banda', function(Banda) {
                            return Banda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('banda.new', {
            parent: 'banda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/banda/banda-dialog.html',
                    controller: 'BandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                localizacion: null,
                                datacreacion: null,
                                anosactivo: null,
                                temas: null,
                                discografica: null,
                                foto: null,
                                fotoContentType: null,
                                logo: null,
                                logoContentType: null,
                                pais: null,
                                estado: null,
                                genero: null,
                                biografia: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('banda', null, { reload: 'banda' });
                }, function() {
                    $state.go('banda');
                });
            }]
        })
        .state('banda.edit', {
            parent: 'banda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/banda/banda-dialog.html',
                    controller: 'BandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Banda', function(Banda) {
                            return Banda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('banda', null, { reload: 'banda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('banda.delete', {
            parent: 'banda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/banda/banda-delete-dialog.html',
                    controller: 'BandaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Banda', function(Banda) {
                            return Banda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('banda', null, { reload: 'banda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
