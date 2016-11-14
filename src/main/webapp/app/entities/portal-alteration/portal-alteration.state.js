(function() {
    'use strict';

    angular
        .module('oncokbApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('portal-alteration', {
            parent: 'entity',
            url: '/portal-alteration',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PortalAlterations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/portal-alteration/portal-alterations.html',
                    controller: 'PortalAlterationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('portal-alteration-detail', {
            parent: 'entity',
            url: '/portal-alteration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PortalAlteration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/portal-alteration/portal-alteration-detail.html',
                    controller: 'PortalAlterationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PortalAlteration', function($stateParams, PortalAlteration) {
                    return PortalAlteration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'portal-alteration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('portal-alteration-detail.edit', {
            parent: 'portal-alteration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portal-alteration/portal-alteration-dialog.html',
                    controller: 'PortalAlterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PortalAlteration', function(PortalAlteration) {
                            return PortalAlteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('portal-alteration.new', {
            parent: 'portal-alteration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portal-alteration/portal-alteration-dialog.html',
                    controller: 'PortalAlterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cancerType: null,
                                cancerStudy: null,
                                sampleId: null,
                                proteinChange: null,
                                proteinStartPosition: null,
                                proteinEndPosition: null,
                                alterationType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('portal-alteration', null, { reload: 'portal-alteration' });
                }, function() {
                    $state.go('portal-alteration');
                });
            }]
        })
        .state('portal-alteration.edit', {
            parent: 'portal-alteration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portal-alteration/portal-alteration-dialog.html',
                    controller: 'PortalAlterationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PortalAlteration', function(PortalAlteration) {
                            return PortalAlteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('portal-alteration', null, { reload: 'portal-alteration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('portal-alteration.delete', {
            parent: 'portal-alteration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portal-alteration/portal-alteration-delete-dialog.html',
                    controller: 'PortalAlterationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PortalAlteration', function(PortalAlteration) {
                            return PortalAlteration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('portal-alteration', null, { reload: 'portal-alteration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
