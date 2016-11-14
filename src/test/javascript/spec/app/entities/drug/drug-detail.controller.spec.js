'use strict';

describe('Controller Tests', function() {

    describe('Drug Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDrug, MockDrugSynonym, MockDrugAtcCode, MockStatus, MockComment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDrug = jasmine.createSpy('MockDrug');
            MockDrugSynonym = jasmine.createSpy('MockDrugSynonym');
            MockDrugAtcCode = jasmine.createSpy('MockDrugAtcCode');
            MockStatus = jasmine.createSpy('MockStatus');
            MockComment = jasmine.createSpy('MockComment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Drug': MockDrug,
                'DrugSynonym': MockDrugSynonym,
                'DrugAtcCode': MockDrugAtcCode,
                'Status': MockStatus,
                'Comment': MockComment
            };
            createController = function() {
                $injector.get('$controller')("DrugDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:drugUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
