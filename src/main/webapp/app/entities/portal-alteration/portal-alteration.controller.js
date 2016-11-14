(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('PortalAlterationController', PortalAlterationController);

    PortalAlterationController.$inject = ['$scope', '$state', 'PortalAlteration'];

    function PortalAlterationController ($scope, $state, PortalAlteration) {
        var vm = this;
        
        vm.portalAlterations = [];

        loadAll();

        function loadAll() {
            PortalAlteration.query(function(result) {
                vm.portalAlterations = result;
            });
        }
    }
})();
