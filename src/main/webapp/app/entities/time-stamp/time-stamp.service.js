(function() {
    'use strict';
    angular
        .module('oncokbApp')
        .factory('TimeStamp', TimeStamp);

    TimeStamp.$inject = ['$resource', 'DateUtils'];

    function TimeStamp ($resource, DateUtils) {
        var resourceUrl =  'api/time-stamps/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timeStamp = DateUtils.convertDateTimeFromServer(data.timeStamp);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
