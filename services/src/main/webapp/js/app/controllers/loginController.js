function LoginCtrl($scope, $location) {

    $scope.connexion = function() {
        console.log("redirect");
        $location.path('/home');
    }
}