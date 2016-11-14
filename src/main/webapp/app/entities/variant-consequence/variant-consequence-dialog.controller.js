(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('VariantConsequenceDialogController', VariantConsequenceDialogController);

    VariantConsequenceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'VariantConsequence'];

    function VariantConsequenceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, VariantConsequence) {
        var vm = this;

        vm.variantConsequence = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.variantConsequence.id !== null) {
                VariantConsequence.update(vm.variantConsequence, onSaveSuccess, onSaveError);
            } else {
                VariantConsequence.save(vm.variantConsequence, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:variantConsequenceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
