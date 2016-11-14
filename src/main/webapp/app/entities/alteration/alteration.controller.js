(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .controller('AlterationController', AlterationController);

    AlterationController.$inject = ['$scope', '$state', 'Alteration'];

    function AlterationController ($scope, $state, Alteration) {
        var vm = this;
        
        vm.alterations = [];

        loadAll();

        function loadAll() {
            Alteration.query(function(result) {
                vm.alterations = result;
            });
        }
    }
})();
