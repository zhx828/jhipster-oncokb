(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneDeleteController',GeneDeleteController);

    GeneDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gene'];

    function GeneDeleteController($uibModalInstance, entity, Gene) {
        var vm = this;

        vm.gene = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gene.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
