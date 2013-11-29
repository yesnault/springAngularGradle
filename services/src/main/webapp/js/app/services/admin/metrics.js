'use strict';

app.factory("MetricsService", function ($http) {
    return {
        getMetrics: function () {
            return $http.get('metrics/metrics');
        }
    }
});
