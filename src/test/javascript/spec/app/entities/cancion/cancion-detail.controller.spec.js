'use strict';

describe('Controller Tests', function() {

    describe('Cancion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCancion, MockAlbum, MockPuntuacionCancion, MockComentarCancion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCancion = jasmine.createSpy('MockCancion');
            MockAlbum = jasmine.createSpy('MockAlbum');
            MockPuntuacionCancion = jasmine.createSpy('MockPuntuacionCancion');
            MockComentarCancion = jasmine.createSpy('MockComentarCancion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Cancion': MockCancion,
                'Album': MockAlbum,
                'PuntuacionCancion': MockPuntuacionCancion,
                'ComentarCancion': MockComentarCancion
            };
            createController = function() {
                $injector.get('$controller')("CancionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:cancionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
