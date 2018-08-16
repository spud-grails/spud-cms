package spud.cms

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

/**
 * See the API for {@link grails.testing.web.interceptor.InterceptorUnitTest} for usage instructions
 */
class SpudCmsInterceptorSpec extends Specification implements InterceptorUnitTest<SpudCmsInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test spudCms interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"page")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
