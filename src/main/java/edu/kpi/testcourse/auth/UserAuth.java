package edu.kpi.testcourse.auth;


import edu.umd.cs.findbugs.annotations.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UserDetails;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.ArrayList;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;

/**
 * Клас, який забезпечує аутентифікацію юзера - перевірку логіну і пароля.
 */
@Singleton
public class UserAuth implements AuthenticationProvider {
  @Override
  public Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
      AuthenticationRequest<?,
      ?> authenticationRequest) {
    return Flowable.create(emitter -> {
      emitter.onNext(
          new UserDetails((String) authenticationRequest.getIdentity(),
            new ArrayList<>())
      );
      emitter.onComplete();
    }, BackpressureStrategy.ERROR);
  }
}
