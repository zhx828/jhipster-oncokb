(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('VariantConsequence', VariantConsequence);

    VariantConsequence.$inject = ['$resource'];

    function VariantConsequence ($resource) {
        var resourceUrl =  'api/variant-consequences/:id';

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
