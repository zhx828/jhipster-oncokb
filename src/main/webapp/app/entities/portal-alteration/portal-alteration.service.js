(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('PortalAlteration', PortalAlteration);

    PortalAlteration.$inject = ['$resource'];

    function PortalAlteration ($resource) {
        var resourceUrl =  'api/portal-alterations/:id';

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
