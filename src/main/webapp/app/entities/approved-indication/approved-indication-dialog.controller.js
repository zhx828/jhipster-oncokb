(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ApprovedIndicationDialogController', ApprovedIndicationDialogController);

    ApprovedIndicationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ApprovedIndication'];

    function ApprovedIndicationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ApprovedIndication) {
        var vm = this;

        vm.approvedIndication = entity;
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
            if (vm.approvedIndication.id !== null) {
                ApprovedIndication.update(vm.approvedIndication, onSaveSuccess, onSaveError);
            } else {
                ApprovedIndication.save(vm.approvedIndication, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:approvedIndicationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
