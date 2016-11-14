(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gene', {
            parent: 'entity',
            url: '/gene',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Genes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gene/genes.html',
                    controller: 'GeneController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('gene-detail', {
            parent: 'entity',
            url: '/gene/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gene'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gene/gene-detail.html',
                    controller: 'GeneDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Gene', function($stateParams, Gene) {
                    return Gene.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gene',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gene-detail.edit', {
            parent: 'gene-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene/gene-dialog.html',
                    controller: 'GeneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gene', function(Gene) {
                            return Gene.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gene.new', {
            parent: 'gene',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene/gene-dialog.html',
                    controller: 'GeneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                entrezGeneId: null,
                                hugoSymbol: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gene', null, { reload: 'gene' });
                }, function() {
                    $state.go('gene');
                });
            }]
        })
        .state('gene.edit', {
            parent: 'gene',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene/gene-dialog.html',
                    controller: 'GeneDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gene', function(Gene) {
                            return Gene.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gene', null, { reload: 'gene' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gene.delete', {
            parent: 'gene',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene/gene-delete-dialog.html',
                    controller: 'GeneDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gene', function(Gene) {
                            return Gene.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gene', null, { reload: 'gene' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
