(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('PortalAlterationDetailController', PortalAlterationDetailController);

    PortalAlterationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PortalAlteration', 'Gene', 'Alteration'];

    function PortalAlterationDetailController($scope, $rootScope, $stateParams, previousState, entity, PortalAlteration, Gene, Alteration) {
        var vm = this;

        vm.portalAlteration = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:portalAlterationUpdate', function(event, result) {
            vm.portalAlteration = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
