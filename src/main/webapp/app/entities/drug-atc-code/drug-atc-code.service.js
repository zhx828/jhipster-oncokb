(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('DrugAtcCode', DrugAtcCode);

    DrugAtcCode.$inject = ['$resource'];

    function DrugAtcCode ($resource) {
        var resourceUrl =  'api/drug-atc-codes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
