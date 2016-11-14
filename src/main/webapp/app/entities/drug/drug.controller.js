(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugController', DrugController);

    DrugController.$inject = ['$scope', '$state', 'Drug'];

    function DrugController ($scope, $state, Drug) {
        var vm = this;
        
        vm.drugs = [];

        loadAll();

        function loadAll() {
            Drug.query(function(result) {
                vm.drugs = result;
            });
        }
    }
})();
