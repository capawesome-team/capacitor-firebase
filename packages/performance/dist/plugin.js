var capacitorFirebasePerformance = (function (exports, core, performance) {
    'use strict';

    const FirebasePerformance = core.registerPlugin('FirebasePerformance', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.FirebasePerformanceWeb()),
    });

    class FirebasePerformanceWeb extends core.WebPlugin {
        constructor() {
            super(...arguments);
            this.traces = {};
        }
        async startTrace(options) {
            const perf = performance.getPerformance();
            const trace = performance.trace(perf, options.traceName);
            trace.start();
            this.traces[options.traceName] = trace;
        }
        async stopTrace(options) {
            const trace = this.traces[options.traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            trace.stop();
            delete this.traces[options.traceName];
        }
        async incrementMetric(options) {
            const trace = this.traces[options.traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            trace.incrementMetric(options.metricName, options.incrementBy);
        }
        async setEnabled(options) {
            const perf = performance.getPerformance();
            perf.instrumentationEnabled = options.enabled;
            perf.dataCollectionEnabled = options.enabled;
        }
        async isEnabled() {
            const perf = performance.getPerformance();
            const result = {
                enabled: perf.instrumentationEnabled || perf.dataCollectionEnabled,
            };
            return result;
        }
        async putAttribute({ traceName, attribute, value, }) {
            const trace = this.traces[traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            trace.putAttribute(attribute, value);
            return;
        }
        async getAttribute({ traceName, attribute, }) {
            var _a;
            const trace = this.traces[traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            return { value: (_a = trace.getAttribute(attribute)) !== null && _a !== undefined ? _a : null };
        }
        async getAttributes({ traceName, }) {
            const trace = this.traces[traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            return { attributes: trace.getAttributes() };
        }
        async removeAttribute({ traceName, attribute, }) {
            const trace = this.traces[traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            trace.removeAttribute(attribute);
        }
        async putMetric({ traceName, metricName, num, }) {
            const trace = this.traces[traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            trace.putMetric(metricName, num);
        }
        async getMetric({ traceName, metricName, }) {
            const trace = this.traces[traceName];
            if (!trace) {
                throw new Error(FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND);
            }
            return { value: trace.getMetric(metricName) };
        }
        async record({ traceName, startTime, duration, options, }) {
            const perf = performance.getPerformance();
            const trace = performance.trace(perf, traceName);
            trace.record(startTime, duration, options);
        }
    }
    FirebasePerformanceWeb.ERROR_TRACE_NOT_FOUND = 'No trace was found with the provided traceName.';

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        FirebasePerformanceWeb: FirebasePerformanceWeb
    });

    exports.FirebasePerformance = FirebasePerformance;

    return exports;

})({}, capacitorExports, firebasePerformanceExports);
//# sourceMappingURL=plugin.js.map
