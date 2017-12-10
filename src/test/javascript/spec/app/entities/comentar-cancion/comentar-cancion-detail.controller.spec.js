'use strict';

describe('Controller Tests', function() {

    describe('ComentarCancion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockComentarCancion, MockUser, MockCancion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockComentarCancion = jasmine.createSpy('MockComentarCancion');
            MockUser = jasmine.createSpy('MockUser');
            MockCancion = jasmine.createSpy('MockCancion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ComentarCancion': MockComentarCancion,
                'User': MockUser,
                'Cancion': MockCancion
            };
            createController = function() {
                $injector.get('$controller')("ComentarCancionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'rocktionaryApp:comentarCancionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
