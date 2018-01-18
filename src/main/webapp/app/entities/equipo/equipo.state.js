angular
    .module('rocktionaryApp')
    .config(stateConfig);


stateConfig.$inject = ['$stateProvider'];

function stateConfig ($stateProvider) {
    return $stateProvider
        .state('equipo', {
            parent: 'entity',
            url: '/equipo',
            data: {
                authorities: ['ROLE_USER']
                //pageTitle: 'rocktionaryApp.banda.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/equipo/equipo.html'
                    // controller: 'EquipoController',
                    // controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('equipo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
}
