(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugDetailController', DrugDetailController);

    DrugDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Drug', 'DrugSynonym', 'DrugAtcCode', 'Status', 'Comment'];

    function DrugDetailController($scope, $rootScope, $stateParams, previousState, entity, Drug, DrugSynonym, DrugAtcCode, Status, Comment) {
        var vm = this;

        vm.drug = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:drugUpdate', function(event, result) {
            vm.drug = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
