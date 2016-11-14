(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('TreatmentDetailController', TreatmentDetailController);

    TreatmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Treatment', 'Status', 'Comment', 'Drug', 'ApprovedIndication'];

    function TreatmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Treatment, Status, Comment, Drug, ApprovedIndication) {
        var vm = this;

        vm.treatment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:treatmentUpdate', function(event, result) {
            vm.treatment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
