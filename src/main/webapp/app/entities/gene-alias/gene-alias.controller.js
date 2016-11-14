(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneAliasController', GeneAliasController);

    GeneAliasController.$inject = ['$scope', '$state', 'GeneAlias'];

    function GeneAliasController ($scope, $state, GeneAlias) {
        var vm = this;
        
        vm.geneAliases = [];

        loadAll();

        function loadAll() {
            GeneAlias.query(function(result) {
                vm.geneAliases = result;
            });
        }
    }
})();
