(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('clinical-trial', {
            parent: 'entity',
            url: '/clinical-trial',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ClinicalTrials'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clinical-trial/clinical-trials.html',
                    controller: 'ClinicalTrialController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('clinical-trial-detail', {
            parent: 'entity',
            url: '/clinical-trial/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ClinicalTrial'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/clinical-trial/clinical-trial-detail.html',
                    controller: 'ClinicalTrialDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ClinicalTrial', function($stateParams, ClinicalTrial) {
                    return ClinicalTrial.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'clinical-trial',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('clinical-trial-detail.edit', {
            parent: 'clinical-trial-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinical-trial/clinical-trial-dialog.html',
                    controller: 'ClinicalTrialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClinicalTrial', function(ClinicalTrial) {
                            return ClinicalTrial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clinical-trial.new', {
            parent: 'clinical-trial',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinical-trial/clinical-trial-dialog.html',
                    controller: 'ClinicalTrialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nctId: null,
                                cdrId: null,
                                title: null,
                                purpose: null,
                                recruitingStatus: null,
                                eligibilityCriteria: null,
                                phase: null,
                                diseaseCondition: null,
                                lastChangedDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('clinical-trial', null, { reload: 'clinical-trial' });
                }, function() {
                    $state.go('clinical-trial');
                });
            }]
        })
        .state('clinical-trial.edit', {
            parent: 'clinical-trial',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinical-trial/clinical-trial-dialog.html',
                    controller: 'ClinicalTrialDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ClinicalTrial', function(ClinicalTrial) {
                            return ClinicalTrial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clinical-trial', null, { reload: 'clinical-trial' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('clinical-trial.delete', {
            parent: 'clinical-trial',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/clinical-trial/clinical-trial-delete-dialog.html',
                    controller: 'ClinicalTrialDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ClinicalTrial', function(ClinicalTrial) {
                            return ClinicalTrial.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('clinical-trial', null, { reload: 'clinical-trial' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
