app.directive('appShowresults', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: "partials/common/showResults.html",
        scope: {
            nbresults: '='
        },
        controller: function ($scope, $attrs) {
            $scope.choiceNbResults = [ 10, 50, 100, 1000, 10000 ];
            if ($attrs.nbresults) {
                $scope.nbResultsToDisplay = parseInt($attrs.nbresults);
            } else {
                $scope.nbResultsToDisplay = 50;
            }
        }
    }
});