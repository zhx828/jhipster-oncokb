(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evidence', {
            parent: 'entity',
            url: '/evidence',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Evidences'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evidence/evidences.html',
                    controller: 'EvidenceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('evidence-detail', {
            parent: 'entity',
            url: '/evidence/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Evidence'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evidence/evidence-detail.html',
                    controller: 'EvidenceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Evidence', function($stateParams, Evidence) {
                    return Evidence.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'evidence',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('evidence-detail.edit', {
            parent: 'evidence-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evidence/evidence-dialog.html',
                    controller: 'EvidenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Evidence', function(Evidence) {
                            return Evidence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evidence.new', {
            parent: 'evidence',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evidence/evidence-dialog.html',
                    controller: 'EvidenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                evidenceType: null,
                                levelOfEvidence: null,
                                knownEffect: null,
                                cancerType: null,
                                subtype: null,
                                description: null,
                                additionalInfo: null,
                                lastEdit: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('evidence', null, { reload: 'evidence' });
                }, function() {
                    $state.go('evidence');
                });
            }]
        })
        .state('evidence.edit', {
            parent: 'evidence',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evidence/evidence-dialog.html',
                    controller: 'EvidenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Evidence', function(Evidence) {
                            return Evidence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evidence', null, { reload: 'evidence' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evidence.delete', {
            parent: 'evidence',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evidence/evidence-delete-dialog.html',
                    controller: 'EvidenceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Evidence', function(Evidence) {
                            return Evidence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evidence', null, { reload: 'evidence' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
