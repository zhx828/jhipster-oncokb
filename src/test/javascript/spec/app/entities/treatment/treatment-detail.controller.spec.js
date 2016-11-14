'use strict';

describe('Controller Tests', function() {

    describe('Treatment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTreatment, MockStatus, MockComment, MockDrug, MockApprovedIndication;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockStatus = jasmine.createSpy('MockStatus');
            MockComment = jasmine.createSpy('MockComment');
            MockDrug = jasmine.createSpy('MockDrug');
            MockApprovedIndication = jasmine.createSpy('MockApprovedIndication');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Treatment': MockTreatment,
                'Status': MockStatus,
                'Comment': MockComment,
                'Drug': MockDrug,
                'ApprovedIndication': MockApprovedIndication
            };
            createController = function() {
                $injector.get('$controller')("TreatmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:treatmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
