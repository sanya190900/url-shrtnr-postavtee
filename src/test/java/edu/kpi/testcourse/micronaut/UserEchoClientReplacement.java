package edu.kpi.testcourse.micronaut;

import edu.kpi.testcourse.rest.UsernameFetcher;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.reactivex.Single;

import javax.inject.Singleton;

@Requires(env = Environment.TEST)
@Singleton
public class UserEchoClientReplacement implements UsernameFetcher {

  @Override
  public Single<String> findUsername() {
    return Single.just("sherlock");
  }
}
