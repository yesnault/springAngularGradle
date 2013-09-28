protoApp.directive('protoShowresults', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: "partials/common/showResults.html",
        controller: function ($scope) {
            $scope.choiceNbResults = [ 10, 50, 100, 1000, 10000 ];
            $scope.nbResults = 50
        }
    }
});