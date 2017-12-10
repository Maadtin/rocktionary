'use strict';

describe('Controller Tests', function() {

    describe('Banda Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockBanda, MockDiscografica, MockComponente, MockPuntuacionBanda, MockComentarBanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockBanda = jasmine.createSpy('MockBanda');
            MockDiscografica = jasmine.createSpy('MockDiscografica');
            MockComponente = jasmine.createSpy('MockComponente');
            MockPuntuacionBanda = jasmine.createSpy('MockPuntuacionBanda');
            MockComentarBanda = jasmine.createSpy('MockComentarBanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Banda': MockBanda,
                'Discografica': MockDiscografica,
                'Componente': MockComponente,
                'PuntuacionBanda': MockPuntuacionBanda,
                'ComentarBanda': MockComentarBanda
            };
            createController = function() {
                $injector.get('$controller')("BandaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:bandaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
