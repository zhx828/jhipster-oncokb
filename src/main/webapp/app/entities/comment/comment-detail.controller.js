(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('CommentDetailController', CommentDetailController);

    CommentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comment', 'Alteration', 'Gene', 'ClinicalTrial', 'Article', 'Drug', 'Evidence', 'Treatment', 'TimeStamp', 'Status'];

    function CommentDetailController($scope, $rootScope, $stateParams, previousState, entity, Comment, Alteration, Gene, ClinicalTrial, Article, Drug, Evidence, Treatment, TimeStamp, Status) {
        var vm = this;

        vm.comment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('oncokbApp:commentUpdate', function(event, result) {
            vm.comment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
