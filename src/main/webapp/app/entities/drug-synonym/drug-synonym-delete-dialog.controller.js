(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugSynonymDeleteController',DrugSynonymDeleteController);

    DrugSynonymDeleteController.$inject = ['$uibModalInstance', 'entity', 'DrugSynonym'];

    function DrugSynonymDeleteController($uibModalInstance, entity, DrugSynonym) {
        var vm = this;

        vm.drugSynonym = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DrugSynonym.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
