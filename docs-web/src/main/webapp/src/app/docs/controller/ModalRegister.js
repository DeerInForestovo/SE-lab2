'use strict';

/**
 * Modal register controller.
 */
angular.module('docs').controller('ModalRegister', function ($scope, $uibModalInstance) {
  $scope.username = '';
  $scope.close = function() {
    $uibModalInstance.close();
  }

  $scope.register = function(username, email, password) {
    $uibModalInstance.close(username, email, password);
  };
});