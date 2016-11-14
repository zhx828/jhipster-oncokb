(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('NccnGuideline', NccnGuideline);

    NccnGuideline.$inject = ['$resource'];

    function NccnGuideline ($resource) {
        var resourceUrl =  'api/nccn-guidelines/:id';

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
