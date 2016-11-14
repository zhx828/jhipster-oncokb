(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneLabelDialogController', GeneLabelDialogController);

    GeneLabelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GeneLabel', 'Gene'];

    function GeneLabelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GeneLabel, Gene) {
        var vm = this;

        vm.geneLabel = entity;
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
            if (vm.geneLabel.id !== null) {
                GeneLabel.update(vm.geneLabel, onSaveSuccess, onSaveError);
            } else {
                GeneLabel.save(vm.geneLabel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:geneLabelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
