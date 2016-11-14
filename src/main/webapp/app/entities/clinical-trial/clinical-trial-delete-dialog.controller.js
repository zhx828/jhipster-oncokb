(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ClinicalTrialDeleteController',ClinicalTrialDeleteController);

    ClinicalTrialDeleteController.$inject = ['$uibModalInstance', 'entity', 'ClinicalTrial'];

    function ClinicalTrialDeleteController($uibModalInstance, entity, ClinicalTrial) {
        var vm = this;

        vm.clinicalTrial = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ClinicalTrial.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
