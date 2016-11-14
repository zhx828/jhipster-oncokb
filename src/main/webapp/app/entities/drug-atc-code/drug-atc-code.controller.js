(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugAtcCodeController', DrugAtcCodeController);

    DrugAtcCodeController.$inject = ['$scope', '$state', 'DrugAtcCode'];

    function DrugAtcCodeController ($scope, $state, DrugAtcCode) {
        var vm = this;
        
        vm.drugAtcCodes = [];

        loadAll();

        function loadAll() {
            DrugAtcCode.query(function(result) {
                vm.drugAtcCodes = result;
            });
        }
    }
})();
