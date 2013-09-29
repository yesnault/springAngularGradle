function UsersCtrl($scope, UsersService) {
    UsersService.findAll().
        success(function (users) {
            $scope.users = users;
        })
        .error(function (resp) {
            console.log("Error with UsersService.findAll" + resp);
        });
}

function UserEditCtrl($scope, User, $routeParams, $location) {

    $scope.title = "Update a user";

    var self = this;

    User.get({userId: $routeParams.userId}, function (user) {
        self.original = user;
        $scope.user = new User(self.original);
    });

    $scope.isClean = function () {
        return angular.equals(self.original, $scope.user);
    }

    $scope.destroy = function () {
        self.original.destroy(function () {
            $location.path('/users');
        });
    };

    $scope.save = function () {
        $scope.user.update(function () {
            $location.path('/users');
        });
    };
}

function UserNewCtrl($scope, User, $location) {

    $scope.title = "Add new user";

    $scope.user = new User({id:'-2'});

    $scope.save = function () {
        $scope.user.$save(
            function() {
                $location.path('/users/')
            }
        );
    }

}