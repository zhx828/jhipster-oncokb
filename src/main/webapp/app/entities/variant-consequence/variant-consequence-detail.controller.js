(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('VariantConsequenceDetailController', VariantConsequenceDetailController);

    VariantConsequenceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'VariantConsequence'];

    function VariantConsequenceDetailController($scope, $rootScope, $stateParams, previousState, entity, VariantConsequence) {
        var vm = this;

        vm.variantConsequence = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:variantConsequenceUpdate', function(event, result) {
            vm.variantConsequence = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
