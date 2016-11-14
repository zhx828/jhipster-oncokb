(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('EvidenceDialogController', EvidenceDialogController);

    EvidenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Evidence', 'Status', 'Comment', 'Gene', 'Alteration', 'Treatment', 'Article', 'ClinicalTrial', 'NccnGuideline'];

    function EvidenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Evidence, Status, Comment, Gene, Alteration, Treatment, Article, ClinicalTrial, NccnGuideline) {
        var vm = this;

        vm.evidence = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.statuses = Status.query();
        vm.comments = Comment.query();
        vm.genes = Gene.query();
        vm.alterations = Alteration.query();
        vm.treatments = Treatment.query();
        vm.articles = Article.query();
        vm.clinicaltrials = ClinicalTrial.query();
        vm.nccnguidelines = NccnGuideline.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evidence.id !== null) {
                Evidence.update(vm.evidence, onSaveSuccess, onSaveError);
            } else {
                Evidence.save(vm.evidence, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:evidenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastEdit = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
