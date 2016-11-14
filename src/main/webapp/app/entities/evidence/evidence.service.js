(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('Evidence', Evidence);

    Evidence.$inject = ['$resource', 'DateUtils'];

    function Evidence ($resource, DateUtils) {
        var resourceUrl =  'api/evidences/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.lastEdit = DateUtils.convertDateTimeFromServer(data.lastEdit);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
