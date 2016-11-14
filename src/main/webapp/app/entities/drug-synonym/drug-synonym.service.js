(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('DrugSynonym', DrugSynonym);

    DrugSynonym.$inject = ['$resource'];

    function DrugSynonym ($resource) {
        var resourceUrl =  'api/drug-synonyms/:id';

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
