(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gene-label', {
            parent: 'entity',
            url: '/gene-label',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeneLabels'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gene-label/gene-labels.html',
                    controller: 'GeneLabelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('gene-label-detail', {
            parent: 'entity',
            url: '/gene-label/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeneLabel'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gene-label/gene-label-detail.html',
                    controller: 'GeneLabelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GeneLabel', function($stateParams, GeneLabel) {
                    return GeneLabel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gene-label',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gene-label-detail.edit', {
            parent: 'gene-label-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-label/gene-label-dialog.html',
                    controller: 'GeneLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeneLabel', function(GeneLabel) {
                            return GeneLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gene-label.new', {
            parent: 'gene-label',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-label/gene-label-dialog.html',
                    controller: 'GeneLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gene-label', null, { reload: 'gene-label' });
                }, function() {
                    $state.go('gene-label');
                });
            }]
        })
        .state('gene-label.edit', {
            parent: 'gene-label',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-label/gene-label-dialog.html',
                    controller: 'GeneLabelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeneLabel', function(GeneLabel) {
                            return GeneLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gene-label', null, { reload: 'gene-label' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gene-label.delete', {
            parent: 'gene-label',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-label/gene-label-delete-dialog.html',
                    controller: 'GeneLabelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GeneLabel', function(GeneLabel) {
                            return GeneLabel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gene-label', null, { reload: 'gene-label' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
