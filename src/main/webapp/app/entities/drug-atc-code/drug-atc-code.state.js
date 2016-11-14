(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('drug-atc-code', {
            parent: 'entity',
            url: '/drug-atc-code',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DrugAtcCodes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug-atc-code/drug-atc-codes.html',
                    controller: 'DrugAtcCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('drug-atc-code-detail', {
            parent: 'entity',
            url: '/drug-atc-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DrugAtcCode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug-atc-code/drug-atc-code-detail.html',
                    controller: 'DrugAtcCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DrugAtcCode', function($stateParams, DrugAtcCode) {
                    return DrugAtcCode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'drug-atc-code',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('drug-atc-code-detail.edit', {
            parent: 'drug-atc-code-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-atc-code/drug-atc-code-dialog.html',
                    controller: 'DrugAtcCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DrugAtcCode', function(DrugAtcCode) {
                            return DrugAtcCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug-atc-code.new', {
            parent: 'drug-atc-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-atc-code/drug-atc-code-dialog.html',
                    controller: 'DrugAtcCodeDialogController',
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
                    $state.go('drug-atc-code', null, { reload: 'drug-atc-code' });
                }, function() {
                    $state.go('drug-atc-code');
                });
            }]
        })
        .state('drug-atc-code.edit', {
            parent: 'drug-atc-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-atc-code/drug-atc-code-dialog.html',
                    controller: 'DrugAtcCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DrugAtcCode', function(DrugAtcCode) {
                            return DrugAtcCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug-atc-code', null, { reload: 'drug-atc-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug-atc-code.delete', {
            parent: 'drug-atc-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-atc-code/drug-atc-code-delete-dialog.html',
                    controller: 'DrugAtcCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DrugAtcCode', function(DrugAtcCode) {
                            return DrugAtcCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug-atc-code', null, { reload: 'drug-atc-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
