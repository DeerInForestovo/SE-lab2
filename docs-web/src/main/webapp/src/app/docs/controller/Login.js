'use strict';

/**
 * Login controller.
 */
angular.module('docs').controller('Login', function(Restangular, $scope, $rootScope, $state, $stateParams, $dialog, User, $translate, $uibModal) {
  $scope.codeRequired = false;

  // Get the app configuration
  Restangular.one('app').get().then(function(data) {
    $rootScope.app = data;
  });

  // Login as guest
  $scope.loginAsGuest = function() {
    $scope.user = {
      username: 'guest',
      password: ''
    };
    $scope.login();
  };
  
  // Login
  $scope.login = function() {
    User.login($scope.user).then(function() {
      User.userInfo(true).then(function(data) {
        $rootScope.userInfo = data;
      });

      if($stateParams.redirectState !== undefined && $stateParams.redirectParams !== undefined) {
        $state.go($stateParams.redirectState, JSON.parse($stateParams.redirectParams))
          .catch(function() {
            $state.go('document.default');
          });
      } else {
        $state.go('document.default');
      }
    }, function(data) {
      if (data.data.type === 'ValidationCodeRequired') {
        // A TOTP validation code is required to login
        $scope.codeRequired = true;
      } else {
        // Login truly failed
        var title = $translate.instant('login.login_failed_title');
        var msg = $translate.instant('login.login_failed_message');
        var btns = [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}];
        $dialog.messageBox(title, msg, btns);
      }
    });
  };

  // Password lost
  $scope.openPasswordLost = function () {
    $uibModal.open({
      templateUrl: 'partial/docs/passwordlost.html',
      controller: 'ModalPasswordLost'
    }).result.then(function (username) {
      if (username === null) {
        return;
      }

      // Send a password lost email
      Restangular.one('user').post('password_lost', {
        username: username
      }).then(function () {
        var title = $translate.instant('login.password_lost_sent_title');
        var msg = $translate.instant('login.password_lost_sent_message', { username: username });
        var btns = [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}];
        $dialog.messageBox(title, msg, btns);
      }, function () {
        var title = $translate.instant('login.password_lost_error_title');
        var msg = $translate.instant('login.password_lost_error_message');
        var btns = [{result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary'}];
        $dialog.messageBox(title, msg, btns);
      });
    });
  };

  // Register
  $scope.openRegister = function () {
    $uibModal.open({
      templateUrl: 'partial/docs/register.html',
      controller: 'ModalRegister'
    }).result.then(function (registerData) {
      if (registerData === null) {
        return;
      }

      Restangular.one('user').post('register_request', {
        email: registerData.email,
        username: registerData.username,
        password: registerData.password
      }).then(function () {
        var title = $translate.instant('register.success_title');
        var msg = $translate.instant('register.success_message', { username: registerData.username });
        var btns = [{ result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary' }];
        $dialog.messageBox(title, msg, btns);
      }, function (error) {
        var title, msg;
        console.log(error);

        if (error.status === 400) {
          title = $translate.instant('register.validation_error_title');
          msg = error.data || $translate.instant('register.validation_error_message');
        } else {
          title = $translate.instant('register.error_title');
          msg = error.data || $translate.instant('register.error_message');
        }

        var btns = [{ result: 'ok', label: $translate.instant('ok'), cssClass: 'btn-primary' }];
        $dialog.messageBox(title, msg, btns);
      });
    });
  };
});