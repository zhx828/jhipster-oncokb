(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('NccnGuidelineController', NccnGuidelineController);

    NccnGuidelineController.$inject = ['$scope', '$state', 'NccnGuideline'];

    function NccnGuidelineController ($scope, $state, NccnGuideline) {
        var vm = this;
        
        vm.nccnGuidelines = [];

        loadAll();

        function loadAll() {
            NccnGuideline.query(function(result) {
                vm.nccnGuidelines = result;
            });
        }
    }
})();
