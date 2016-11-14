(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('TimeStampDetailController', TimeStampDetailController);

    TimeStampDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TimeStamp', 'User'];

    function TimeStampDetailController($scope, $rootScope, $stateParams, previousState, entity, TimeStamp, User) {
        var vm = this;

        vm.timeStamp = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:timeStampUpdate', function(event, result) {
            vm.timeStamp = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
