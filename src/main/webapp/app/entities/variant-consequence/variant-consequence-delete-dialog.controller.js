(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('VariantConsequenceDeleteController',VariantConsequenceDeleteController);

    VariantConsequenceDeleteController.$inject = ['$uibModalInstance', 'entity', 'VariantConsequence'];

    function VariantConsequenceDeleteController($uibModalInstance, entity, VariantConsequence) {
        var vm = this;

        vm.variantConsequence = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            VariantConsequence.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
