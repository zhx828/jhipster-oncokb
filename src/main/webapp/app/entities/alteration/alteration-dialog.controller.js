(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('AlterationDialogController', AlterationDialogController);

    AlterationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Alteration', 'Status', 'Comment', 'Gene', 'VariantConsequence', 'PortalAlteration'];

    function AlterationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Alteration, Status, Comment, Gene, VariantConsequence, PortalAlteration) {
        var vm = this;

        vm.alteration = entity;
        vm.clear = clear;
        vm.save = save;
        vm.statuses = Status.query();
        vm.comments = Comment.query();
        vm.genes = Gene.query();
        vm.variantconsequences = VariantConsequence.query();
        vm.portalalterations = PortalAlteration.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.alteration.id !== null) {
                Alteration.update(vm.alteration, onSaveSuccess, onSaveError);
            } else {
                Alteration.save(vm.alteration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:alterationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
