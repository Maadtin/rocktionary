'use strict';

describe('Controller Tests', function() {

    describe('Discografica Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDiscografica, MockAlbum, MockBanda;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDiscografica = jasmine.createSpy('MockDiscografica');
            MockAlbum = jasmine.createSpy('MockAlbum');
            MockBanda = jasmine.createSpy('MockBanda');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Discografica': MockDiscografica,
                'Album': MockAlbum,
                'Banda': MockBanda
            };
            createController = function() {
                $injector.get('$controller')("DiscograficaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:discograficaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
