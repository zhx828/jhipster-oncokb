(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneAliasDetailController', GeneAliasDetailController);

    GeneAliasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GeneAlias', 'Gene'];

    function GeneAliasDetailController($scope, $rootScope, $stateParams, previousState, entity, GeneAlias, Gene) {
        var vm = this;

        vm.geneAlias = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:geneAliasUpdate', function(event, result) {
            vm.geneAlias = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
