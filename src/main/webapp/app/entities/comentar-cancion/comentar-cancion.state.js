(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comentar-cancion', {
            parent: 'entity',
            url: '/comentar-cancion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.comentarCancion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentar-cancion/comentar-cancions.html',
                    controller: 'ComentarCancionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentarCancion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('comentar-cancion-detail', {
            parent: 'comentar-cancion',
            url: '/comentar-cancion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.comentarCancion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentar-cancion/comentar-cancion-detail.html',
                    controller: 'ComentarCancionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentarCancion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ComentarCancion', function($stateParams, ComentarCancion) {
                    return ComentarCancion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comentar-cancion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comentar-cancion-detail.edit', {
            parent: 'comentar-cancion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-cancion/comentar-cancion-dialog.html',
                    controller: 'ComentarCancionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ComentarCancion', function(ComentarCancion) {
                            return ComentarCancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentar-cancion.new', {
            parent: 'comentar-cancion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-cancion/comentar-cancion-dialog.html',
                    controller: 'ComentarCancionDialogController',
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
                    $state.go('comentar-cancion', null, { reload: 'comentar-cancion' });
                }, function() {
                    $state.go('comentar-cancion');
                });
            }]
        })
        .state('comentar-cancion.edit', {
            parent: 'comentar-cancion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-cancion/comentar-cancion-dialog.html',
                    controller: 'ComentarCancionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ComentarCancion', function(ComentarCancion) {
                            return ComentarCancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentar-cancion', null, { reload: 'comentar-cancion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentar-cancion.delete', {
            parent: 'comentar-cancion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-cancion/comentar-cancion-delete-dialog.html',
                    controller: 'ComentarCancionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ComentarCancion', function(ComentarCancion) {
                            return ComentarCancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentar-cancion', null, { reload: 'comentar-cancion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
