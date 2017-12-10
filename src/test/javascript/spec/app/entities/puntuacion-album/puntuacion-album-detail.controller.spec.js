'use strict';

describe('Controller Tests', function() {

    describe('PuntuacionAlbum Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPuntuacionAlbum, MockUser, MockAlbum;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPuntuacionAlbum = jasmine.createSpy('MockPuntuacionAlbum');
            MockUser = jasmine.createSpy('MockUser');
            MockAlbum = jasmine.createSpy('MockAlbum');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PuntuacionAlbum': MockPuntuacionAlbum,
                'User': MockUser,
                'Album': MockAlbum
            };
            createController = function() {
                $injector.get('$controller')("PuntuacionAlbumDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:puntuacionAlbumUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
