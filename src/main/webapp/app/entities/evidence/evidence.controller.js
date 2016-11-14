(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('EvidenceController', EvidenceController);

    EvidenceController.$inject = ['$scope', '$state', 'Evidence'];

    function EvidenceController ($scope, $state, Evidence) {
        var vm = this;
        
        vm.evidences = [];

        loadAll();

        function loadAll() {
            Evidence.query(function(result) {
                vm.evidences = result;
            });
        }
    }
})();
