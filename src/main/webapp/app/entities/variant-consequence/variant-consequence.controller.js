(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('VariantConsequenceController', VariantConsequenceController);

    VariantConsequenceController.$inject = ['$scope', '$state', 'VariantConsequence'];

    function VariantConsequenceController ($scope, $state, VariantConsequence) {
        var vm = this;
        
        vm.variantConsequences = [];

        loadAll();

        function loadAll() {
            VariantConsequence.query(function(result) {
                vm.variantConsequences = result;
            });
        }
    }
})();
