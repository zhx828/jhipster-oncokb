(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('PortalAlterationDeleteController',PortalAlterationDeleteController);

    PortalAlterationDeleteController.$inject = ['$uibModalInstance', 'entity', 'PortalAlteration'];

    function PortalAlterationDeleteController($uibModalInstance, entity, PortalAlteration) {
        var vm = this;

        vm.portalAlteration = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PortalAlteration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
