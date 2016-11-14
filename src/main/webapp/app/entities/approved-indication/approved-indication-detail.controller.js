(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('ApprovedIndicationDetailController', ApprovedIndicationDetailController);

    ApprovedIndicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ApprovedIndication'];

    function ApprovedIndicationDetailController($scope, $rootScope, $stateParams, previousState, entity, ApprovedIndication) {
        var vm = this;

        vm.approvedIndication = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:approvedIndicationUpdate', function(event, result) {
            vm.approvedIndication = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
