(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comentar-banda', {
            parent: 'entity',
            url: '/comentar-banda',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.comentarBanda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentar-banda/comentar-bandas.html',
                    controller: 'ComentarBandaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentarBanda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('comentar-banda-detail', {
            parent: 'comentar-banda',
            url: '/comentar-banda/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.comentarBanda.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentar-banda/comentar-banda-detail.html',
                    controller: 'ComentarBandaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentarBanda');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ComentarBanda', function($stateParams, ComentarBanda) {
                    return ComentarBanda.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comentar-banda',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comentar-banda-detail.edit', {
            parent: 'comentar-banda-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-banda/comentar-banda-dialog.html',
                    controller: 'ComentarBandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ComentarBanda', function(ComentarBanda) {
                            return ComentarBanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentar-banda.new', {
            parent: 'comentar-banda',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-banda/comentar-banda-dialog.html',
                    controller: 'ComentarBandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comentario: null,
                                fechaComentario: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comentar-banda', null, { reload: 'comentar-banda' });
                }, function() {
                    $state.go('comentar-banda');
                });
            }]
        })
        .state('comentar-banda.edit', {
            parent: 'comentar-banda',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-banda/comentar-banda-dialog.html',
                    controller: 'ComentarBandaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ComentarBanda', function(ComentarBanda) {
                            return ComentarBanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentar-banda', null, { reload: 'comentar-banda' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentar-banda.delete', {
            parent: 'comentar-banda',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-banda/comentar-banda-delete-dialog.html',
                    controller: 'ComentarBandaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ComentarBanda', function(ComentarBanda) {
                            return ComentarBanda.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentar-banda', null, { reload: 'comentar-banda' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
