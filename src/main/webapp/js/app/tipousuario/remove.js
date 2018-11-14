'use strict'

moduleTipousuario.controller('tipousuarioRemoveController', ['$scope', '$http', 'toolService', '$routeParams','sessionService',
    function ($scope, $http,toolService, $routeParams,sessionService) {

            $http({
                method: 'GET',
                withCredentials: true,
                url: '/json?ob=tipousuario&op=get&id='+$routeParams.id
            }).then(function (response) {
                $scope.status = response.status;
                $scope.ajaxDataTipoUsuarios = response.data.message;
            }, function (response) {
                $scope.ajaxDataTipoUsuarios = response.data.message || 'Request failed';
                $scope.status = response.status;
            });
        if(sessionService){
            $scope.usuariologeado=sessionService.getUserName();
           $scope.ocultar= true;
        }
            $scope.borrar = function () {
                $http({
                    method: 'GET',
                    withCredentials: true,
                    url: '/json?ob=tipousuario&op=remove&id='+$routeParams.id
                }).then(function (response) {
                    $scope.status = response.status;
                    $scope.ajaxDataTipoUsuarios = response.data.message;
                    $scope.mensaje = true;
                }, function (response) {
                    $scope.ajaxDataTipoUsuarios = response.data.message || 'Request failed';
                    $scope.status = response.status;
                    $scope.mensajeError = true;
                }); 
            };
            $scope.doTheBack = function() {
                window.history.back();
              };


        $scope.isActive = toolService.isActive;

    }]);