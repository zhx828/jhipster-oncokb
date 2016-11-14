(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugSynonymDialogController', DrugSynonymDialogController);

    DrugSynonymDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DrugSynonym', 'Drug'];

    function DrugSynonymDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DrugSynonym, Drug) {
        var vm = this;

        vm.drugSynonym = entity;
        vm.clear = clear;
        vm.save = save;
        vm.drugs = Drug.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.drugSynonym.id !== null) {
                DrugSynonym.update(vm.drugSynonym, onSaveSuccess, onSaveError);
            } else {
                DrugSynonym.save(vm.drugSynonym, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:drugSynonymUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
