(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('DrugSynonymController', DrugSynonymController);

    DrugSynonymController.$inject = ['$scope', '$state', 'DrugSynonym'];

    function DrugSynonymController ($scope, $state, DrugSynonym) {
        var vm = this;
        
        vm.drugSynonyms = [];

        loadAll();

        function loadAll() {
            DrugSynonym.query(function(result) {
                vm.drugSynonyms = result;
            });
        }
    }
})();
