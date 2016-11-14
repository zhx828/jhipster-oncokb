'use strict';

describe('Controller Tests', function() {

    describe('PortalAlteration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPortalAlteration, MockGene, MockAlteration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPortalAlteration = jasmine.createSpy('MockPortalAlteration');
            MockGene = jasmine.createSpy('MockGene');
            MockAlteration = jasmine.createSpy('MockAlteration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PortalAlteration': MockPortalAlteration,
                'Gene': MockGene,
                'Alteration': MockAlteration
            };
            createController = function() {
                $injector.get('$controller')("PortalAlterationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:portalAlterationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
