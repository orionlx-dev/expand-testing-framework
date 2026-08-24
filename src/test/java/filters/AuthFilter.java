package filters;

import ctx.AuthContext;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final AuthContext authContext;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        String token = authContext.getToken();

        if (token != null) {
            requestSpec.header(
                    "X-Auth-Token", token
            );
        }

        return ctx.next(requestSpec, responseSpec);
    }
}
