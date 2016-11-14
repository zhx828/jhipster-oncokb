(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('NccnGuidelineDialogController', NccnGuidelineDialogController);

    NccnGuidelineDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'NccnGuideline'];

    function NccnGuidelineDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, NccnGuideline) {
        var vm = this;

        vm.nccnGuideline = entity;
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
            if (vm.nccnGuideline.id !== null) {
                NccnGuideline.update(vm.nccnGuideline, onSaveSuccess, onSaveError);
            } else {
                NccnGuideline.save(vm.nccnGuideline, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:nccnGuidelineUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
