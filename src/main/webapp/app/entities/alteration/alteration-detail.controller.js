(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('AlterationDetailController', AlterationDetailController);

    AlterationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Alteration', 'Status', 'Comment', 'Gene', 'VariantConsequence', 'PortalAlteration'];

    function AlterationDetailController($scope, $rootScope, $stateParams, previousState, entity, Alteration, Status, Comment, Gene, VariantConsequence, PortalAlteration) {
        var vm = this;

        vm.alteration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:alterationUpdate', function(event, result) {
            vm.alteration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
