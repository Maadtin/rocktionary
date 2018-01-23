angular
    .module('rocktionaryApp')
    .service('AppUtils', ['$sce', function ($sce) {
        this.parseMillis = function(millis) {
            let minutes = Math.floor(millis / 60000);
            let seconds = ((millis % 60000) / 1000).toFixed(0);
            return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
        }

        this.validateUrl = function (url) {
            return $sce.trustAsResourceUrl(url);
        }

        this.truncateString = function (string, number) {
            return string.substring(0, number);
        }

    }]);
