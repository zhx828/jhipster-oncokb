(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ApprovedIndicationController', ApprovedIndicationController);

    ApprovedIndicationController.$inject = ['$scope', '$state', 'ApprovedIndication'];

    function ApprovedIndicationController ($scope, $state, ApprovedIndication) {
        var vm = this;
        
        vm.approvedIndications = [];

        loadAll();

        function loadAll() {
            ApprovedIndication.query(function(result) {
                vm.approvedIndications = result;
            });
        }
    }
})();
