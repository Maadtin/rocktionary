'use strict';

describe('Controller Tests', function() {

    describe('ComentarBanda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockComentarBanda, MockUser, MockBanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockComentarBanda = jasmine.createSpy('MockComentarBanda');
            MockUser = jasmine.createSpy('MockUser');
            MockBanda = jasmine.createSpy('MockBanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ComentarBanda': MockComentarBanda,
                'User': MockUser,
                'Banda': MockBanda
            };
            createController = function() {
                $injector.get('$controller')("ComentarBandaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:comentarBandaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
