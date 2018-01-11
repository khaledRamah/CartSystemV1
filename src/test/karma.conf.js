// Karma configuration
// Generated on Wed Dec 27 2017 10:01:29 GMT+0200 (EET)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [

        './node_modules/angular/angular.js',                             // angular
        './node_modules/angular-ui-router/release/angular-ui-router.js', // ui-router
        './node_modules/angular-resource.min.js', // for ngResource
        './node_modules/angular-mocks/angular-mocks.js',
        '..//main/Frontend/css/bootstrap.css',
        '..//main/Frontend/css/style.css',
        '..//main/Frontend/css/font-awesome.css',
        '..//main/Frontend/js/jquery-1.11.1.min.js',
        '..//main/Frontend/HomePageModule.js',
        '..//main/Frontend/css/font-awesome.css',
        '..//main/Frontend/index.html',
        'Cart.test.js'
    ],


    // list of files / patterns to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
