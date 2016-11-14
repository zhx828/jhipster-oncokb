(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('TreatmentController', TreatmentController);

    TreatmentController.$inject = ['$scope', '$state', 'Treatment'];

    function TreatmentController ($scope, $state, Treatment) {
        var vm = this;
        
        vm.treatments = [];

        loadAll();

        function loadAll() {
            Treatment.query(function(result) {
                vm.treatments = result;
            });
        }
    }
})();
