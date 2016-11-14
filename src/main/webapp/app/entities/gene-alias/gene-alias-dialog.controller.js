(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneAliasDialogController', GeneAliasDialogController);

    GeneAliasDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GeneAlias', 'Gene'];

    function GeneAliasDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GeneAlias, Gene) {
        var vm = this;

        vm.geneAlias = entity;
        vm.clear = clear;
        vm.save = save;
        vm.genes = Gene.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.geneAlias.id !== null) {
                GeneAlias.update(vm.geneAlias, onSaveSuccess, onSaveError);
            } else {
                GeneAlias.save(vm.geneAlias, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:geneAliasUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
