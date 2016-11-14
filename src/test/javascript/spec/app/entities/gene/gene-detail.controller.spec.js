'use strict';

describe('Controller Tests', function() {

    describe('Gene Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGene, MockGeneAlias, MockGeneLabel, MockStatus, MockComment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGene = jasmine.createSpy('MockGene');
            MockGeneAlias = jasmine.createSpy('MockGeneAlias');
            MockGeneLabel = jasmine.createSpy('MockGeneLabel');
            MockStatus = jasmine.createSpy('MockStatus');
            MockComment = jasmine.createSpy('MockComment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Gene': MockGene,
                'GeneAlias': MockGeneAlias,
                'GeneLabel': MockGeneLabel,
                'Status': MockStatus,
                'Comment': MockComment
            };
            createController = function() {
                $injector.get('$controller')("GeneDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'oncokbApp:geneUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
