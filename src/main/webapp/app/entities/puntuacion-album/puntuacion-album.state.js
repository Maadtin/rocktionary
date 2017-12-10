(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('puntuacion-album', {
            parent: 'entity',
            url: '/puntuacion-album',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.puntuacionAlbum.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-album/puntuacion-albums.html',
                    controller: 'PuntuacionAlbumController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionAlbum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('puntuacion-album-detail', {
            parent: 'puntuacion-album',
            url: '/puntuacion-album/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.puntuacionAlbum.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-album/puntuacion-album-detail.html',
                    controller: 'PuntuacionAlbumDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionAlbum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PuntuacionAlbum', function($stateParams, PuntuacionAlbum) {
                    return PuntuacionAlbum.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'puntuacion-album',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('puntuacion-album-detail.edit', {
            parent: 'puntuacion-album-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-album/puntuacion-album-dialog.html',
                    controller: 'PuntuacionAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionAlbum', function(PuntuacionAlbum) {
                            return PuntuacionAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-album.new', {
            parent: 'puntuacion-album',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-album/puntuacion-album-dialog.html',
                    controller: 'PuntuacionAlbumDialogController',
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
                    $state.go('puntuacion-album', null, { reload: 'puntuacion-album' });
                }, function() {
                    $state.go('puntuacion-album');
                });
            }]
        })
        .state('puntuacion-album.edit', {
            parent: 'puntuacion-album',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-album/puntuacion-album-dialog.html',
                    controller: 'PuntuacionAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionAlbum', function(PuntuacionAlbum) {
                            return PuntuacionAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-album', null, { reload: 'puntuacion-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-album.delete', {
            parent: 'puntuacion-album',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-album/puntuacion-album-delete-dialog.html',
                    controller: 'PuntuacionAlbumDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PuntuacionAlbum', function(PuntuacionAlbum) {
                            return PuntuacionAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-album', null, { reload: 'puntuacion-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
