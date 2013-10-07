function LogsCtrl($scope, LogsService) {

    $scope.findAll = function () {
        LogsService.findAll().
            success(function (loggers) {
                $scope.loggers = loggers;
            })
            .error(function (resp) {
                console.log("Error with LogsService.findAll" + resp);
            });
    }

    $scope.changeLevel = function(loggerName, newLevel) {
        LogsService.changeLevel(loggerName, newLevel).
            success(function(loggers) {
                console.log("Success changing Level for " + loggerName + " to " + newLevel);
                $scope.findAll();
            })
            .error(function(resp) {
               console.log("Error with LogsService.changeLevel : " + resp)
            });
    }

    $scope.findAll();


}