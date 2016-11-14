(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneDetailController', GeneDetailController);

    GeneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gene', 'GeneAlias', 'GeneLabel', 'Status', 'Comment'];

    function GeneDetailController($scope, $rootScope, $stateParams, previousState, entity, Gene, GeneAlias, GeneLabel, Status, Comment) {
        var vm = this;

        vm.gene = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:geneUpdate', function(event, result) {
            vm.gene = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
