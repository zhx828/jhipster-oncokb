(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ClinicalTrialDialogController', ClinicalTrialDialogController);

    ClinicalTrialDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ClinicalTrial', 'Status', 'Comment', 'Drug', 'Country'];

    function ClinicalTrialDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ClinicalTrial, Status, Comment, Drug, Country) {
        var vm = this;

        vm.clinicalTrial = entity;
        vm.clear = clear;
        vm.save = save;
        vm.statuses = Status.query();
        vm.comments = Comment.query();
        vm.drugs = Drug.query();
        vm.countries = Country.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.clinicalTrial.id !== null) {
                ClinicalTrial.update(vm.clinicalTrial, onSaveSuccess, onSaveError);
            } else {
                ClinicalTrial.save(vm.clinicalTrial, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:clinicalTrialUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
