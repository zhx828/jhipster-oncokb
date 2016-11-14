(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('ClinicalTrial', ClinicalTrial);

    ClinicalTrial.$inject = ['$resource'];

    function ClinicalTrial ($resource) {
        var resourceUrl =  'api/clinical-trials/:id';

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
