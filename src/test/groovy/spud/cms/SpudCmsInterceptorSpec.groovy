package spud.cms


import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SpudCmsInterceptor)
class SpudCmsInterceptorSpec extends Specification {

    def setup() {
    }

    def cleanup() {

    }

    void "Test spudCms interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"spudCms")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
