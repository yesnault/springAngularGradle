'use strict';

app.factory("LogsService", function ($http) {
    return {
        findAll: function () {
            return $http.get('rest/logs');
        },
        changeLevel: function (loggerName, newLevel) {
            return $http.get('rest/logs/change/' + loggerName + '/' + newLevel);
        }
    }
});
