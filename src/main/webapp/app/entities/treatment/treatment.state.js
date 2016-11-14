(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('treatment', {
            parent: 'entity',
            url: '/treatment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Treatments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/treatment/treatments.html',
                    controller: 'TreatmentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('treatment-detail', {
            parent: 'entity',
            url: '/treatment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Treatment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/treatment/treatment-detail.html',
                    controller: 'TreatmentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Treatment', function($stateParams, Treatment) {
                    return Treatment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'treatment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('treatment-detail.edit', {
            parent: 'treatment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/treatment/treatment-dialog.html',
                    controller: 'TreatmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Treatment', function(Treatment) {
                            return Treatment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('treatment.new', {
            parent: 'treatment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/treatment/treatment-dialog.html',
                    controller: 'TreatmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('treatment', null, { reload: 'treatment' });
                }, function() {
                    $state.go('treatment');
                });
            }]
        })
        .state('treatment.edit', {
            parent: 'treatment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/treatment/treatment-dialog.html',
                    controller: 'TreatmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Treatment', function(Treatment) {
                            return Treatment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('treatment', null, { reload: 'treatment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('treatment.delete', {
            parent: 'treatment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/treatment/treatment-delete-dialog.html',
                    controller: 'TreatmentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Treatment', function(Treatment) {
                            return Treatment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('treatment', null, { reload: 'treatment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
