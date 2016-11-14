(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('PortalAlterationDialogController', PortalAlterationDialogController);

    PortalAlterationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PortalAlteration', 'Gene', 'Alteration'];

    function PortalAlterationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PortalAlteration, Gene, Alteration) {
        var vm = this;

        vm.portalAlteration = entity;
        vm.clear = clear;
        vm.save = save;
        vm.genes = Gene.query();
        vm.alterations = Alteration.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.portalAlteration.id !== null) {
                PortalAlteration.update(vm.portalAlteration, onSaveSuccess, onSaveError);
            } else {
                PortalAlteration.save(vm.portalAlteration, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:portalAlterationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
