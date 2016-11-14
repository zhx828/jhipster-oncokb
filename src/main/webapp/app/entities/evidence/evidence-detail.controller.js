(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('EvidenceDetailController', EvidenceDetailController);

    EvidenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Evidence', 'Status', 'Comment', 'Gene', 'Alteration', 'Treatment', 'Article', 'ClinicalTrial', 'NccnGuideline'];

    function EvidenceDetailController($scope, $rootScope, $stateParams, previousState, entity, Evidence, Status, Comment, Gene, Alteration, Treatment, Article, ClinicalTrial, NccnGuideline) {
        var vm = this;

        vm.evidence = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:evidenceUpdate', function(event, result) {
            vm.evidence = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
