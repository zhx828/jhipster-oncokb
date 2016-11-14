(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('drug-synonym', {
            parent: 'entity',
            url: '/drug-synonym',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DrugSynonyms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug-synonym/drug-synonyms.html',
                    controller: 'DrugSynonymController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('drug-synonym-detail', {
            parent: 'entity',
            url: '/drug-synonym/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'DrugSynonym'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug-synonym/drug-synonym-detail.html',
                    controller: 'DrugSynonymDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'DrugSynonym', function($stateParams, DrugSynonym) {
                    return DrugSynonym.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'drug-synonym',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('drug-synonym-detail.edit', {
            parent: 'drug-synonym-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-synonym/drug-synonym-dialog.html',
                    controller: 'DrugSynonymDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DrugSynonym', function(DrugSynonym) {
                            return DrugSynonym.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug-synonym.new', {
            parent: 'drug-synonym',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-synonym/drug-synonym-dialog.html',
                    controller: 'DrugSynonymDialogController',
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
                    $state.go('drug-synonym', null, { reload: 'drug-synonym' });
                }, function() {
                    $state.go('drug-synonym');
                });
            }]
        })
        .state('drug-synonym.edit', {
            parent: 'drug-synonym',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-synonym/drug-synonym-dialog.html',
                    controller: 'DrugSynonymDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DrugSynonym', function(DrugSynonym) {
                            return DrugSynonym.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug-synonym', null, { reload: 'drug-synonym' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug-synonym.delete', {
            parent: 'drug-synonym',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-synonym/drug-synonym-delete-dialog.html',
                    controller: 'DrugSynonymDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DrugSynonym', function(DrugSynonym) {
                            return DrugSynonym.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug-synonym', null, { reload: 'drug-synonym' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
