(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugAtcCodeDialogController', DrugAtcCodeDialogController);

    DrugAtcCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'DrugAtcCode', 'Drug'];

    function DrugAtcCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, DrugAtcCode, Drug) {
        var vm = this;

        vm.drugAtcCode = entity;
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
            if (vm.drugAtcCode.id !== null) {
                DrugAtcCode.update(vm.drugAtcCode, onSaveSuccess, onSaveError);
            } else {
                DrugAtcCode.save(vm.drugAtcCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:drugAtcCodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
