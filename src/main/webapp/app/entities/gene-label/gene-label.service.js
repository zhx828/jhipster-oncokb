(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('GeneLabel', GeneLabel);

    GeneLabel.$inject = ['$resource'];

    function GeneLabel ($resource) {
        var resourceUrl =  'api/gene-labels/:id';

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
