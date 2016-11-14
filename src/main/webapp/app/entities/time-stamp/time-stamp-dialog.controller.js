(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('TimeStampDialogController', TimeStampDialogController);

    TimeStampDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimeStamp', 'User'];

    function TimeStampDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TimeStamp, User) {
        var vm = this;

        vm.timeStamp = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.timeStamp.id !== null) {
                TimeStamp.update(vm.timeStamp, onSaveSuccess, onSaveError);
            } else {
                TimeStamp.save(vm.timeStamp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('oncokbApp:timeStampUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timeStamp = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
