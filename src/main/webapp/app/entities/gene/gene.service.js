(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('Gene', Gene);

    Gene.$inject = ['$resource'];

    function Gene ($resource) {
        var resourceUrl =  'api/genes/:id';

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
