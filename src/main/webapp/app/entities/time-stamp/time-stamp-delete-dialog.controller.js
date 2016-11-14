(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('TimeStampDeleteController',TimeStampDeleteController);

    TimeStampDeleteController.$inject = ['$uibModalInstance', 'entity', 'TimeStamp'];

    function TimeStampDeleteController($uibModalInstance, entity, TimeStamp) {
        var vm = this;

        vm.timeStamp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TimeStamp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
