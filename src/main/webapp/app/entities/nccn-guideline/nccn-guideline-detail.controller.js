(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('NccnGuidelineDetailController', NccnGuidelineDetailController);

    NccnGuidelineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'NccnGuideline'];

    function NccnGuidelineDetailController($scope, $rootScope, $stateParams, previousState, entity, NccnGuideline) {
        var vm = this;

        vm.nccnGuideline = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:nccnGuidelineUpdate', function(event, result) {
            vm.nccnGuideline = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
