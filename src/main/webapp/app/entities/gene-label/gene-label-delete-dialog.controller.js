(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneLabelDeleteController',GeneLabelDeleteController);

    GeneLabelDeleteController.$inject = ['$uibModalInstance', 'entity', 'GeneLabel'];

    function GeneLabelDeleteController($uibModalInstance, entity, GeneLabel) {
        var vm = this;

        vm.geneLabel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GeneLabel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
