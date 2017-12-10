(function() {
    'use strict';

    angular
        .module('rocktionaryApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comentar-album', {
            parent: 'entity',
            url: '/comentar-album',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.comentarAlbum.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentar-album/comentar-albums.html',
                    controller: 'ComentarAlbumController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentarAlbum');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('comentar-album-detail', {
            parent: 'comentar-album',
            url: '/comentar-album/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rocktionaryApp.comentarAlbum.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comentar-album/comentar-album-detail.html',
                    controller: 'ComentarAlbumDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('comentarAlbum');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ComentarAlbum', function($stateParams, ComentarAlbum) {
                    return ComentarAlbum.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comentar-album',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comentar-album-detail.edit', {
            parent: 'comentar-album-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-album/comentar-album-dialog.html',
                    controller: 'ComentarAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ComentarAlbum', function(ComentarAlbum) {
                            return ComentarAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentar-album.new', {
            parent: 'comentar-album',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-album/comentar-album-dialog.html',
                    controller: 'ComentarAlbumDialogController',
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
                    $state.go('comentar-album', null, { reload: 'comentar-album' });
                }, function() {
                    $state.go('comentar-album');
                });
            }]
        })
        .state('comentar-album.edit', {
            parent: 'comentar-album',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-album/comentar-album-dialog.html',
                    controller: 'ComentarAlbumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ComentarAlbum', function(ComentarAlbum) {
                            return ComentarAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentar-album', null, { reload: 'comentar-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comentar-album.delete', {
            parent: 'comentar-album',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comentar-album/comentar-album-delete-dialog.html',
                    controller: 'ComentarAlbumDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ComentarAlbum', function(ComentarAlbum) {
                            return ComentarAlbum.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comentar-album', null, { reload: 'comentar-album' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
