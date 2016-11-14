(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('time-stamp', {
            parent: 'entity',
            url: '/time-stamp',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TimeStamps'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/time-stamp/time-stamps.html',
                    controller: 'TimeStampController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('time-stamp-detail', {
            parent: 'entity',
            url: '/time-stamp/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TimeStamp'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/time-stamp/time-stamp-detail.html',
                    controller: 'TimeStampDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TimeStamp', function($stateParams, TimeStamp) {
                    return TimeStamp.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'time-stamp',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('time-stamp-detail.edit', {
            parent: 'time-stamp-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/time-stamp/time-stamp-dialog.html',
                    controller: 'TimeStampDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TimeStamp', function(TimeStamp) {
                            return TimeStamp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('time-stamp.new', {
            parent: 'time-stamp',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/time-stamp/time-stamp-dialog.html',
                    controller: 'TimeStampDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                timeStamp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('time-stamp', null, { reload: 'time-stamp' });
                }, function() {
                    $state.go('time-stamp');
                });
            }]
        })
        .state('time-stamp.edit', {
            parent: 'time-stamp',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/time-stamp/time-stamp-dialog.html',
                    controller: 'TimeStampDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TimeStamp', function(TimeStamp) {
                            return TimeStamp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('time-stamp', null, { reload: 'time-stamp' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('time-stamp.delete', {
            parent: 'time-stamp',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/time-stamp/time-stamp-delete-dialog.html',
                    controller: 'TimeStampDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TimeStamp', function(TimeStamp) {
                            return TimeStamp.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('time-stamp', null, { reload: 'time-stamp' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
