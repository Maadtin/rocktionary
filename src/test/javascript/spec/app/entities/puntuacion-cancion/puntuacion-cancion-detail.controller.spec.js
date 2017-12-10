'use strict';

describe('Controller Tests', function() {

    describe('PuntuacionCancion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPuntuacionCancion, MockUser, MockCancion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPuntuacionCancion = jasmine.createSpy('MockPuntuacionCancion');
            MockUser = jasmine.createSpy('MockUser');
            MockCancion = jasmine.createSpy('MockCancion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PuntuacionCancion': MockPuntuacionCancion,
                'User': MockUser,
                'Cancion': MockCancion
            };
            createController = function() {
                $injector.get('$controller')("PuntuacionCancionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:puntuacionCancionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
