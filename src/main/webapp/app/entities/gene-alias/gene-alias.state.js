(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gene-alias', {
            parent: 'entity',
            url: '/gene-alias',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeneAliases'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gene-alias/gene-aliases.html',
                    controller: 'GeneAliasController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('gene-alias-detail', {
            parent: 'entity',
            url: '/gene-alias/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GeneAlias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gene-alias/gene-alias-detail.html',
                    controller: 'GeneAliasDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GeneAlias', function($stateParams, GeneAlias) {
                    return GeneAlias.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gene-alias',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gene-alias-detail.edit', {
            parent: 'gene-alias-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-alias/gene-alias-dialog.html',
                    controller: 'GeneAliasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeneAlias', function(GeneAlias) {
                            return GeneAlias.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gene-alias.new', {
            parent: 'gene-alias',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-alias/gene-alias-dialog.html',
                    controller: 'GeneAliasDialogController',
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
                    $state.go('gene-alias', null, { reload: 'gene-alias' });
                }, function() {
                    $state.go('gene-alias');
                });
            }]
        })
        .state('gene-alias.edit', {
            parent: 'gene-alias',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-alias/gene-alias-dialog.html',
                    controller: 'GeneAliasDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GeneAlias', function(GeneAlias) {
                            return GeneAlias.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gene-alias', null, { reload: 'gene-alias' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gene-alias.delete', {
            parent: 'gene-alias',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gene-alias/gene-alias-delete-dialog.html',
                    controller: 'GeneAliasDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GeneAlias', function(GeneAlias) {
                            return GeneAlias.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gene-alias', null, { reload: 'gene-alias' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
