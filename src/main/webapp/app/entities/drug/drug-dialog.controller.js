(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugDialogController', DrugDialogController);

    DrugDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Drug', 'DrugSynonym', 'DrugAtcCode', 'Status', 'Comment'];

    function DrugDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Drug, DrugSynonym, DrugAtcCode, Status, Comment) {
        var vm = this;

        vm.drug = entity;
        vm.clear = clear;
        vm.save = save;
        vm.drugsynonyms = DrugSynonym.query();
        vm.drugatccodes = DrugAtcCode.query();
        vm.statuses = Status.query();
        vm.comments = Comment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.drug.id !== null) {
                Drug.update(vm.drug, onSaveSuccess, onSaveError);
            } else {
                Drug.save(vm.drug, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:drugUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
