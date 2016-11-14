'use strict';

describe('Controller Tests', function() {

    describe('Alteration Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlteration, MockStatus, MockComment, MockGene, MockVariantConsequence, MockPortalAlteration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlteration = jasmine.createSpy('MockAlteration');
            MockStatus = jasmine.createSpy('MockStatus');
            MockComment = jasmine.createSpy('MockComment');
            MockGene = jasmine.createSpy('MockGene');
            MockVariantConsequence = jasmine.createSpy('MockVariantConsequence');
            MockPortalAlteration = jasmine.createSpy('MockPortalAlteration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Alteration': MockAlteration,
                'Status': MockStatus,
                'Comment': MockComment,
                'Gene': MockGene,
                'VariantConsequence': MockVariantConsequence,
                'PortalAlteration': MockPortalAlteration
            };
            createController = function() {
                $injector.get('$controller')("AlterationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:alterationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
