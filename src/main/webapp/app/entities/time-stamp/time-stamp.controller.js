(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('TimeStampController', TimeStampController);

    TimeStampController.$inject = ['$scope', '$state', 'TimeStamp'];

    function TimeStampController ($scope, $state, TimeStamp) {
        var vm = this;
        
        vm.timeStamps = [];

        loadAll();

        function loadAll() {
            TimeStamp.query(function(result) {
                vm.timeStamps = result;
            });
        }
    }
})();
