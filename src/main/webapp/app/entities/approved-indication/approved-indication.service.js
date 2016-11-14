(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('ApprovedIndication', ApprovedIndication);

    ApprovedIndication.$inject = ['$resource'];

    function ApprovedIndication ($resource) {
        var resourceUrl =  'api/approved-indications/:id';

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
