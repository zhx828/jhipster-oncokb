(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ApprovedIndicationDeleteController',ApprovedIndicationDeleteController);

    ApprovedIndicationDeleteController.$inject = ['$uibModalInstance', 'entity', 'ApprovedIndication'];

    function ApprovedIndicationDeleteController($uibModalInstance, entity, ApprovedIndication) {
        var vm = this;

        vm.approvedIndication = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ApprovedIndication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
