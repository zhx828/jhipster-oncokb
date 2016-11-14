(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugAtcCodeDetailController', DrugAtcCodeDetailController);

    DrugAtcCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DrugAtcCode', 'Drug'];

    function DrugAtcCodeDetailController($scope, $rootScope, $stateParams, previousState, entity, DrugAtcCode, Drug) {
        var vm = this;

        vm.drugAtcCode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:drugAtcCodeUpdate', function(event, result) {
            vm.drugAtcCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
