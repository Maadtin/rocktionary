// (function() {
//     'use strict';
//
//     angular
//         .module('rocktionaryApp')
//         .config(stateConfig);
//
//     stateConfig.$inject = ['$stateProvider'];
//
//     function stateConfig($stateProvider) {
//         $stateProvider
//         .state('album', {
//             parent: 'entity',
//             url: '/album',
//             data: {
//                 authorities: ['ROLE_USER'],
//                 pageTitle: 'rocktionaryApp.album.home.title'
//             },
//             views: {
//                 'content@': {
//                     templateUrl: 'app/entities/album/albums.html',
//                     controller: 'AlbumController',
//                     controllerAs: 'vm'
//                 }
//             },
//             resolve: {
//                 translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                     $translatePartialLoader.addPart('album');
//                     $translatePartialLoader.addPart('global');
//                     return $translate.refresh();
//                 }]
//             }
//         })
//         .state('album-detail', {
//             parent: 'album',
//             url: '/album/{id}',
//             data: {
//                 authorities: ['ROLE_USER'],
//                 pageTitle: 'rocktionaryApp.album.detail.title'
//             },
//             views: {
//                 'content@': {
//                     templateUrl: 'app/entities/album/album-detail.html',
//                     controller: 'AlbumDetailController',
//                     controllerAs: 'vm'
//                 }
//             },
//             resolve: {
//                 translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
//                     $translatePartialLoader.addPart('album');
//                     return $translate.refresh();
//                 }],
//                 entity: ['$stateParams', 'Album', function($stateParams, Album) {
//                     return Album.get({id : $stateParams.id}).$promise;
//                 }],
//                 previousState: ["$state", function ($state) {
//                     var currentStateData = {
//                         name: $state.current.name || 'album',
//                         params: $state.params,
//                         url: $state.href($state.current.name, $state.params)
//                     };
//                     return currentStateData;
//                 }]
//             }
//         })
//         .state('album-detail.edit', {
//             parent: 'album-detail',
//             url: '/detail/edit',
//             data: {
//                 authorities: ['ROLE_USER']
//             },
//             onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
//                 $uibModal.open({
//                     templateUrl: 'app/entities/album/album-dialog.html',
//                     controller: 'AlbumDialogController',
//                     controllerAs: 'vm',
//                     backdrop: 'static',
//                     size: 'lg',
//                     resolve: {
//                         entity: ['Album', function(Album) {
//                             return Album.get({id : $stateParams.id}).$promise;
//                         }]
//                     }
//                 }).result.then(function() {
//                     $state.go('^', {}, { reload: false });
//                 }, function() {
//                     $state.go('^');
//                 });
//             }]
//         })
//         .state('album.new', {
//             parent: 'album',
//             url: '/new',
//             data: {
//                 authorities: ['ROLE_USER']
//             },
//             onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
//                 $uibModal.open({
//                     templateUrl: 'app/entities/album/album-dialog.html',
//                     controller: 'AlbumDialogController',
//                     controllerAs: 'vm',
//                     backdrop: 'static',
//                     size: 'lg',
//                     resolve: {
//                         entity: function () {
//                             return {
//                                 genero: null,
//                                 nombre: null,
//                                 numCanciones: null,
//                                 formato: null,
//                                 numCopias: null,
//                                 reviews: null,
//                                 id: null
//                             };
//                         }
//                     }
//                 }).result.then(function() {
//                     $state.go('album', null, { reload: 'album' });
//                 }, function() {
//                     $state.go('album');
//                 });
//             }]
//         })
//         .state('album.edit', {
//             parent: 'album',
//             url: '/{id}/edit',
//             data: {
//                 authorities: ['ROLE_USER']
//             },
//             onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
//                 $uibModal.open({
//                     templateUrl: 'app/entities/album/album-dialog.html',
//                     controller: 'AlbumDialogController',
//                     controllerAs: 'vm',
//                     backdrop: 'static',
//                     size: 'lg',
//                     resolve: {
//                         entity: ['Album', function(Album) {
//                             return Album.get({id : $stateParams.id}).$promise;
//                         }]
//                     }
//                 }).result.then(function() {
//                     $state.go('album', null, { reload: 'album' });
//                 }, function() {
//                     $state.go('^');
//                 });
//             }]
//         })
//         .state('album.delete', {
//             parent: 'album',
//             url: '/{id}/delete',
//             data: {
//                 authorities: ['ROLE_USER']
//             },
//             onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
//                 $uibModal.open({
//                     templateUrl: 'app/entities/album/album-delete-dialog.html',
//                     controller: 'AlbumDeleteController',
//                     controllerAs: 'vm',
//                     size: 'md',
//                     resolve: {
//                         entity: ['Album', function(Album) {
//                             return Album.get({id : $stateParams.id}).$promise;
//                         }]
//                     }
//                 }).result.then(function() {
//                     $state.go('album', null, { reload: 'album' });
//                 }, function() {
//                     $state.go('^');
//                 });
//             }]
//         });
//     }
//
// })();


angular
    .module('rocktionaryApp')
    .config(stateConfig);


stateConfig.$inject = ['$stateProvider'];

function stateConfig ($stateProvider) {

    return $stateProvider
        .state('album', {
            parent: 'entity',
            url: '/album/{id}',
            data: {
                authorities: ['ROLE_USER']
                //pageTitle: 'rocktionaryApp.banda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/album/album-detail.html',
                    controller: 'AlbumController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('banda');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }],
                album: ['$stateParams', 'AlbumService', function($stateParams, AlbumService) {
                    return  AlbumService.getAlbum().get({id : $stateParams.id}).$promise
                }],
                albumTracks: ['$stateParams', 'AlbumService', function ($stateParams, AlbumService) {
                    return AlbumService.getAlbumTracks().get({id: $stateParams.id}).$promise
                }]
                // topTracks: ['$stateParams', 'AlbumService', function ($stateParams, AlbumService) {
                //     return AlbumService.getTopTracks().get({id: $stateParams.id}).$promise
                // }]
            }
        })


}
