(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('EvidenceDeleteController',EvidenceDeleteController);

    EvidenceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Evidence'];

    function EvidenceDeleteController($uibModalInstance, entity, Evidence) {
        var vm = this;

        vm.evidence = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Evidence.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
