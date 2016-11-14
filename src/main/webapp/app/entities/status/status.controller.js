(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('StatusController', StatusController);

    StatusController.$inject = ['$scope', '$state', 'Status'];

    function StatusController ($scope, $state, Status) {
        var vm = this;
        
        vm.statuses = [];

        loadAll();

        function loadAll() {
            Status.query(function(result) {
                vm.statuses = result;
            });
        }
    }
})();
