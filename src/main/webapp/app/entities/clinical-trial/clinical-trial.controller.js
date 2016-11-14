(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ClinicalTrialController', ClinicalTrialController);

    ClinicalTrialController.$inject = ['$scope', '$state', 'ClinicalTrial'];

    function ClinicalTrialController ($scope, $state, ClinicalTrial) {
        var vm = this;
        
        vm.clinicalTrials = [];

        loadAll();

        function loadAll() {
            ClinicalTrial.query(function(result) {
                vm.clinicalTrials = result;
            });
        }
    }
})();
