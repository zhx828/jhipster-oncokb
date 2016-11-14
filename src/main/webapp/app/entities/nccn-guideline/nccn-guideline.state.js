(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('nccn-guideline', {
            parent: 'entity',
            url: '/nccn-guideline',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'NccnGuidelines'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nccn-guideline/nccn-guidelines.html',
                    controller: 'NccnGuidelineController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('nccn-guideline-detail', {
            parent: 'entity',
            url: '/nccn-guideline/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'NccnGuideline'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/nccn-guideline/nccn-guideline-detail.html',
                    controller: 'NccnGuidelineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'NccnGuideline', function($stateParams, NccnGuideline) {
                    return NccnGuideline.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'nccn-guideline',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('nccn-guideline-detail.edit', {
            parent: 'nccn-guideline-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nccn-guideline/nccn-guideline-dialog.html',
                    controller: 'NccnGuidelineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NccnGuideline', function(NccnGuideline) {
                            return NccnGuideline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('nccn-guideline.new', {
            parent: 'nccn-guideline',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nccn-guideline/nccn-guideline-dialog.html',
                    controller: 'NccnGuidelineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                disease: null,
                                version: null,
                                pages: null,
                                category: null,
                                description: null,
                                additionalInfo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('nccn-guideline', null, { reload: 'nccn-guideline' });
                }, function() {
                    $state.go('nccn-guideline');
                });
            }]
        })
        .state('nccn-guideline.edit', {
            parent: 'nccn-guideline',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nccn-guideline/nccn-guideline-dialog.html',
                    controller: 'NccnGuidelineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['NccnGuideline', function(NccnGuideline) {
                            return NccnGuideline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nccn-guideline', null, { reload: 'nccn-guideline' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('nccn-guideline.delete', {
            parent: 'nccn-guideline',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/nccn-guideline/nccn-guideline-delete-dialog.html',
                    controller: 'NccnGuidelineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['NccnGuideline', function(NccnGuideline) {
                            return NccnGuideline.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('nccn-guideline', null, { reload: 'nccn-guideline' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
