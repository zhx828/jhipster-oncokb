(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('TreatmentDialogController', TreatmentDialogController);

    TreatmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Treatment', 'Status', 'Comment', 'Drug', 'ApprovedIndication'];

    function TreatmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Treatment, Status, Comment, Drug, ApprovedIndication) {
        var vm = this;

        vm.treatment = entity;
        vm.clear = clear;
        vm.save = save;
        vm.statuses = Status.query();
        vm.comments = Comment.query();
        vm.drugs = Drug.query();
        vm.approvedindications = ApprovedIndication.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.treatment.id !== null) {
                Treatment.update(vm.treatment, onSaveSuccess, onSaveError);
            } else {
                Treatment.save(vm.treatment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:treatmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
