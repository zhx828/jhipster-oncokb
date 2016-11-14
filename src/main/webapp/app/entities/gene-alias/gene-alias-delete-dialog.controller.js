(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneAliasDeleteController',GeneAliasDeleteController);

    GeneAliasDeleteController.$inject = ['$uibModalInstance', 'entity', 'GeneAlias'];

    function GeneAliasDeleteController($uibModalInstance, entity, GeneAlias) {
        var vm = this;

        vm.geneAlias = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GeneAlias.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
