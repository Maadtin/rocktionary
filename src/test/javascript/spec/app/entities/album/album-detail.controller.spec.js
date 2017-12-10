'use strict';

describe('Controller Tests', function() {

    describe('Album Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlbum, MockDiscografica, MockPuntuacionAlbum, MockComentarAlbum, MockCancion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlbum = jasmine.createSpy('MockAlbum');
            MockDiscografica = jasmine.createSpy('MockDiscografica');
            MockPuntuacionAlbum = jasmine.createSpy('MockPuntuacionAlbum');
            MockComentarAlbum = jasmine.createSpy('MockComentarAlbum');
            MockCancion = jasmine.createSpy('MockCancion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Album': MockAlbum,
                'Discografica': MockDiscografica,
                'PuntuacionAlbum': MockPuntuacionAlbum,
                'ComentarAlbum': MockComentarAlbum,
                'Cancion': MockCancion
            };
            createController = function() {
                $injector.get('$controller')("AlbumDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:albumUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
