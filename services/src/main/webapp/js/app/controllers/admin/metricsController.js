function MetricsCtrl($scope, MetricsService) {

    MetricsService.getMetrics().
        success(function (metrics) {
            $scope.metrics = metrics;
        })
        .error(function (resp) {
            console.log("Error with MetricsService.findAll" + resp);
        });

}
