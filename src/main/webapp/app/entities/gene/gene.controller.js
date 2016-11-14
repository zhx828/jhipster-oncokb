(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneController', GeneController);

    GeneController.$inject = ['$scope', '$state', 'Gene'];

    function GeneController ($scope, $state, Gene) {
        var vm = this;
        
        vm.genes = [];

        loadAll();

        function loadAll() {
            Gene.query(function(result) {
                vm.genes = result;
            });
        }
    }
})();
