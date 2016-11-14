(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugAtcCodeDeleteController',DrugAtcCodeDeleteController);

    DrugAtcCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'DrugAtcCode'];

    function DrugAtcCodeDeleteController($uibModalInstance, entity, DrugAtcCode) {
        var vm = this;

        vm.drugAtcCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DrugAtcCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
