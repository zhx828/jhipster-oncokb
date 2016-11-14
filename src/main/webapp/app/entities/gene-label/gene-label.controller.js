(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('GeneLabelController', GeneLabelController);

    GeneLabelController.$inject = ['$scope', '$state', 'GeneLabel'];

    function GeneLabelController ($scope, $state, GeneLabel) {
        var vm = this;
        
        vm.geneLabels = [];

        loadAll();

        function loadAll() {
            GeneLabel.query(function(result) {
                vm.geneLabels = result;
            });
        }
    }
})();
