(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ClinicalTrialDetailController', ClinicalTrialDetailController);

    ClinicalTrialDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ClinicalTrial', 'Status', 'Comment', 'Drug', 'Country'];

    function ClinicalTrialDetailController($scope, $rootScope, $stateParams, previousState, entity, ClinicalTrial, Status, Comment, Drug, Country) {
        var vm = this;

        vm.clinicalTrial = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:clinicalTrialUpdate', function(event, result) {
            vm.clinicalTrial = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
