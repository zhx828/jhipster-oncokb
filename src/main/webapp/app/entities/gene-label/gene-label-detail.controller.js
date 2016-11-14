(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneLabelDetailController', GeneLabelDetailController);

    GeneLabelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GeneLabel', 'Gene'];

    function GeneLabelDetailController($scope, $rootScope, $stateParams, previousState, entity, GeneLabel, Gene) {
        var vm = this;

        vm.geneLabel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:geneLabelUpdate', function(event, result) {
            vm.geneLabel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
