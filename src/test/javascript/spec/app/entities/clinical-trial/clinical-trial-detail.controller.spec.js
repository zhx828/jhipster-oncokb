'use strict';

describe('Controller Tests', function() {

    describe('ClinicalTrial Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockClinicalTrial, MockStatus, MockComment, MockDrug, MockCountry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockClinicalTrial = jasmine.createSpy('MockClinicalTrial');
            MockStatus = jasmine.createSpy('MockStatus');
            MockComment = jasmine.createSpy('MockComment');
            MockDrug = jasmine.createSpy('MockDrug');
            MockCountry = jasmine.createSpy('MockCountry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ClinicalTrial': MockClinicalTrial,
                'Status': MockStatus,
                'Comment': MockComment,
                'Drug': MockDrug,
                'Country': MockCountry
            };
            createController = function() {
                $injector.get('$controller')("ClinicalTrialDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:clinicalTrialUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
