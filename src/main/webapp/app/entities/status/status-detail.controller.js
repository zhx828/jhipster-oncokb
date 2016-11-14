(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('StatusDetailController', StatusDetailController);

    StatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Status', 'Alteration', 'Gene', 'ClinicalTrial', 'Article', 'Drug', 'Evidence', 'Treatment', 'TimeStamp', 'Comment'];

    function StatusDetailController($scope, $rootScope, $stateParams, previousState, entity, Status, Alteration, Gene, ClinicalTrial, Article, Drug, Evidence, Treatment, TimeStamp, Comment) {
        var vm = this;

        vm.status = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:statusUpdate', function(event, result) {
            vm.status = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
