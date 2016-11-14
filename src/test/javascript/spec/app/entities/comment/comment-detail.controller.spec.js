'use strict';

describe('Controller Tests', function() {

    describe('Comment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockComment, MockAlteration, MockGene, MockClinicalTrial, MockArticle, MockDrug, MockEvidence, MockTreatment, MockTimeStamp, MockStatus;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockComment = jasmine.createSpy('MockComment');
            MockAlteration = jasmine.createSpy('MockAlteration');
            MockGene = jasmine.createSpy('MockGene');
            MockClinicalTrial = jasmine.createSpy('MockClinicalTrial');
            MockArticle = jasmine.createSpy('MockArticle');
            MockDrug = jasmine.createSpy('MockDrug');
            MockEvidence = jasmine.createSpy('MockEvidence');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockTimeStamp = jasmine.createSpy('MockTimeStamp');
            MockStatus = jasmine.createSpy('MockStatus');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Comment': MockComment,
                'Alteration': MockAlteration,
                'Gene': MockGene,
                'ClinicalTrial': MockClinicalTrial,
                'Article': MockArticle,
                'Drug': MockDrug,
                'Evidence': MockEvidence,
                'Treatment': MockTreatment,
                'TimeStamp': MockTimeStamp,
                'Status': MockStatus
            };
            createController = function() {
                $injector.get('$controller')("CommentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:commentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
