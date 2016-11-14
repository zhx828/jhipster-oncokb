(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneDialogController', GeneDialogController);

    GeneDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gene', 'GeneAlias', 'GeneLabel', 'Status', 'Comment'];

    function GeneDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gene, GeneAlias, GeneLabel, Status, Comment) {
        var vm = this;

        vm.gene = entity;
        vm.clear = clear;
        vm.save = save;
        vm.genealiases = GeneAlias.query();
        vm.genelabels = GeneLabel.query();
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
            if (vm.gene.id !== null) {
                Gene.update(vm.gene, onSaveSuccess, onSaveError);
            } else {
                Gene.save(vm.gene, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:geneUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
