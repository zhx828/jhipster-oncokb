'use strict';

describe('Controller Tests', function() {

    describe('Evidence Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvidence, MockStatus, MockComment, MockGene, MockAlteration, MockTreatment, MockArticle, MockClinicalTrial, MockNccnGuideline;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvidence = jasmine.createSpy('MockEvidence');
            MockStatus = jasmine.createSpy('MockStatus');
            MockComment = jasmine.createSpy('MockComment');
            MockGene = jasmine.createSpy('MockGene');
            MockAlteration = jasmine.createSpy('MockAlteration');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockArticle = jasmine.createSpy('MockArticle');
            MockClinicalTrial = jasmine.createSpy('MockClinicalTrial');
            MockNccnGuideline = jasmine.createSpy('MockNccnGuideline');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Evidence': MockEvidence,
                'Status': MockStatus,
                'Comment': MockComment,
                'Gene': MockGene,
                'Alteration': MockAlteration,
                'Treatment': MockTreatment,
                'Article': MockArticle,
                'ClinicalTrial': MockClinicalTrial,
                'NccnGuideline': MockNccnGuideline
            };
            createController = function() {
                $injector.get('$controller')("EvidenceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:evidenceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
