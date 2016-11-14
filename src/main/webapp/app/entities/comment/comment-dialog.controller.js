(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('CommentDialogController', CommentDialogController);

    CommentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Comment', 'Alteration', 'Gene', 'ClinicalTrial', 'Article', 'Drug', 'Evidence', 'Treatment', 'TimeStamp', 'Status'];

    function CommentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Comment, Alteration, Gene, ClinicalTrial, Article, Drug, Evidence, Treatment, TimeStamp, Status) {
        var vm = this;

        vm.comment = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterations = Alteration.query();
        vm.genes = Gene.query();
        vm.clinicaltrials = ClinicalTrial.query();
        vm.articles = Article.query();
        vm.drugs = Drug.query();
        vm.evidences = Evidence.query();
        vm.treatments = Treatment.query();
        vm.timestamps = TimeStamp.query({filter: 'comment-is-null'});
        $q.all([vm.comment.$promise, vm.timestamps.$promise]).then(function() {
            if (!vm.comment.timeStamp || !vm.comment.timeStamp.id) {
                return $q.reject();
            }
            return TimeStamp.get({id : vm.comment.timeStamp.id}).$promise;
        }).then(function(timeStamp) {
            vm.timestamps.push(timeStamp);
        });
        vm.statuses = Status.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.comment.id !== null) {
                Comment.update(vm.comment, onSaveSuccess, onSaveError);
            } else {
                Comment.save(vm.comment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:commentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
