(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('StatusDialogController', StatusDialogController);

    StatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Status', 'Alteration', 'Gene', 'ClinicalTrial', 'Article', 'Drug', 'Evidence', 'Treatment', 'TimeStamp', 'Comment'];

    function StatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Status, Alteration, Gene, ClinicalTrial, Article, Drug, Evidence, Treatment, TimeStamp, Comment) {
        var vm = this;

        vm.status = entity;
        vm.clear = clear;
        vm.save = save;
        vm.alterations = Alteration.query();
        vm.genes = Gene.query();
        vm.clinicaltrials = ClinicalTrial.query();
        vm.articles = Article.query();
        vm.drugs = Drug.query();
        vm.evidences = Evidence.query();
        vm.treatments = Treatment.query();
        vm.timestamps = TimeStamp.query({filter: 'status-is-null'});
        $q.all([vm.status.$promise, vm.timestamps.$promise]).then(function() {
            if (!vm.status.timeStamp || !vm.status.timeStamp.id) {
                return $q.reject();
            }
            return TimeStamp.get({id : vm.status.timeStamp.id}).$promise;
        }).then(function(timeStamp) {
            vm.timestamps.push(timeStamp);
        });
        vm.comments = Comment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.status.id !== null) {
                Status.update(vm.status, onSaveSuccess, onSaveError);
            } else {
                Status.save(vm.status, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:statusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
