'use strict';

angular.module('docs').controller('SettingsRequests', function ($scope, Restangular, $dialog, $translate) {
  $scope.requests = [];

  console.log("register request load");

  function loadRequests() {
    console.log("load")
    Restangular.one('user').all('register_request').getList().then(function (data) {
      $scope.requests = data;
    });
  }

  $scope.approve = function (req) {
    Restangular.one('user').one('register_request', req.id).post('decision', { decision: 'accept' }).then(function () {
      req.status = 'accepted';
    });
  };

  $scope.reject = function (req) {
    Restangular.one('user').one('register_request', req.id).post('decision', { decision: 'reject' }).then(function () {
      req.status = 'rejected';
    });
  };

  loadRequests();
});
