(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugSynonymDetailController', DrugSynonymDetailController);

    DrugSynonymDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DrugSynonym', 'Drug'];

    function DrugSynonymDetailController($scope, $rootScope, $stateParams, previousState, entity, DrugSynonym, Drug) {
        var vm = this;

        vm.drugSynonym = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:drugSynonymUpdate', function(event, result) {
            vm.drugSynonym = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
