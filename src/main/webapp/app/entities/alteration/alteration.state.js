(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('alteration', {
            parent: 'entity',
            url: '/alteration',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Alterations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration/alterations.html',
                    controller: 'AlterationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('alteration-detail', {
            parent: 'entity',
            url: '/alteration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Alteration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/alteration/alteration-detail.html',
                    controller: 'AlterationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Alteration', function($stateParams, Alteration) {
                    return Alteration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'alteration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('alteration-detail.edit', {
            parent: 'alteration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alteration-dialog.html',
                    controller: 'AlterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alteration', function(Alteration) {
                            return Alteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration.new', {
            parent: 'alteration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alteration-dialog.html',
                    controller: 'AlterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                alteration: null,
                                name: null,
                                alterationType: null,
                                refResidues: null,
                                proteinStart: null,
                                poteinEnd: null,
                                variantResidues: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('alteration', null, { reload: 'alteration' });
                }, function() {
                    $state.go('alteration');
                });
            }]
        })
        .state('alteration.edit', {
            parent: 'alteration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alteration-dialog.html',
                    controller: 'AlterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Alteration', function(Alteration) {
                            return Alteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration', null, { reload: 'alteration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('alteration.delete', {
            parent: 'alteration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/alteration/alteration-delete-dialog.html',
                    controller: 'AlterationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Alteration', function(Alteration) {
                            return Alteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('alteration', null, { reload: 'alteration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
