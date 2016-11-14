(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('approved-indication', {
            parent: 'entity',
            url: '/approved-indication',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ApprovedIndications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/approved-indication/approved-indications.html',
                    controller: 'ApprovedIndicationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('approved-indication-detail', {
            parent: 'entity',
            url: '/approved-indication/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ApprovedIndication'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/approved-indication/approved-indication-detail.html',
                    controller: 'ApprovedIndicationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ApprovedIndication', function($stateParams, ApprovedIndication) {
                    return ApprovedIndication.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'approved-indication',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('approved-indication-detail.edit', {
            parent: 'approved-indication-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/approved-indication/approved-indication-dialog.html',
                    controller: 'ApprovedIndicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApprovedIndication', function(ApprovedIndication) {
                            return ApprovedIndication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('approved-indication.new', {
            parent: 'approved-indication',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/approved-indication/approved-indication-dialog.html',
                    controller: 'ApprovedIndicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                approvedIndications: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('approved-indication', null, { reload: 'approved-indication' });
                }, function() {
                    $state.go('approved-indication');
                });
            }]
        })
        .state('approved-indication.edit', {
            parent: 'approved-indication',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/approved-indication/approved-indication-dialog.html',
                    controller: 'ApprovedIndicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ApprovedIndication', function(ApprovedIndication) {
                            return ApprovedIndication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('approved-indication', null, { reload: 'approved-indication' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('approved-indication.delete', {
            parent: 'approved-indication',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/approved-indication/approved-indication-delete-dialog.html',
                    controller: 'ApprovedIndicationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ApprovedIndication', function(ApprovedIndication) {
                            return ApprovedIndication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('approved-indication', null, { reload: 'approved-indication' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
