(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('Alteration', Alteration);

    Alteration.$inject = ['$resource'];

    function Alteration ($resource) {
        var resourceUrl =  'api/alterations/:id';

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
