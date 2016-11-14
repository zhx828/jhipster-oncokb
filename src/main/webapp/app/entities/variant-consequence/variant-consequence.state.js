(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('variant-consequence', {
            parent: 'entity',
            url: '/variant-consequence',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VariantConsequences'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variant-consequence/variant-consequences.html',
                    controller: 'VariantConsequenceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('variant-consequence-detail', {
            parent: 'entity',
            url: '/variant-consequence/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'VariantConsequence'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variant-consequence/variant-consequence-detail.html',
                    controller: 'VariantConsequenceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'VariantConsequence', function($stateParams, VariantConsequence) {
                    return VariantConsequence.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'variant-consequence',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('variant-consequence-detail.edit', {
            parent: 'variant-consequence-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-consequence/variant-consequence-dialog.html',
                    controller: 'VariantConsequenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VariantConsequence', function(VariantConsequence) {
                            return VariantConsequence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variant-consequence.new', {
            parent: 'variant-consequence',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-consequence/variant-consequence-dialog.html',
                    controller: 'VariantConsequenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                term: null,
                                isGenerallyTruncating: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('variant-consequence', null, { reload: 'variant-consequence' });
                }, function() {
                    $state.go('variant-consequence');
                });
            }]
        })
        .state('variant-consequence.edit', {
            parent: 'variant-consequence',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-consequence/variant-consequence-dialog.html',
                    controller: 'VariantConsequenceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['VariantConsequence', function(VariantConsequence) {
                            return VariantConsequence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variant-consequence', null, { reload: 'variant-consequence' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variant-consequence.delete', {
            parent: 'variant-consequence',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variant-consequence/variant-consequence-delete-dialog.html',
                    controller: 'VariantConsequenceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['VariantConsequence', function(VariantConsequence) {
                            return VariantConsequence.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variant-consequence', null, { reload: 'variant-consequence' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
