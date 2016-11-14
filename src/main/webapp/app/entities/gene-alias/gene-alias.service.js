(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('GeneAlias', GeneAlias);

    GeneAlias.$inject = ['$resource'];

    function GeneAlias ($resource) {
        var resourceUrl =  'api/gene-aliases/:id';

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
