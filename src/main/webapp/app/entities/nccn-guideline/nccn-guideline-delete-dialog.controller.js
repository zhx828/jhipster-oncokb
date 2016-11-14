(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('NccnGuidelineDeleteController',NccnGuidelineDeleteController);

    NccnGuidelineDeleteController.$inject = ['$uibModalInstance', 'entity', 'NccnGuideline'];

    function NccnGuidelineDeleteController($uibModalInstance, entity, NccnGuideline) {
        var vm = this;

        vm.nccnGuideline = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            NccnGuideline.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
