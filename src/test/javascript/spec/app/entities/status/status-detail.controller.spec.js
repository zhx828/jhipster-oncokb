'use strict';

describe('Controller Tests', function() {

    describe('Status Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStatus, MockAlteration, MockGene, MockClinicalTrial, MockArticle, MockDrug, MockEvidence, MockTreatment, MockTimeStamp, MockComment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStatus = jasmine.createSpy('MockStatus');
            MockAlteration = jasmine.createSpy('MockAlteration');
            MockGene = jasmine.createSpy('MockGene');
            MockClinicalTrial = jasmine.createSpy('MockClinicalTrial');
            MockArticle = jasmine.createSpy('MockArticle');
            MockDrug = jasmine.createSpy('MockDrug');
            MockEvidence = jasmine.createSpy('MockEvidence');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockTimeStamp = jasmine.createSpy('MockTimeStamp');
            MockComment = jasmine.createSpy('MockComment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Status': MockStatus,
                'Alteration': MockAlteration,
                'Gene': MockGene,
                'ClinicalTrial': MockClinicalTrial,
                'Article': MockArticle,
                'Drug': MockDrug,
                'Evidence': MockEvidence,
                'Treatment': MockTreatment,
                'TimeStamp': MockTimeStamp,
                'Comment': MockComment
            };
            createController = function() {
                $injector.get('$controller')("StatusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:statusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
