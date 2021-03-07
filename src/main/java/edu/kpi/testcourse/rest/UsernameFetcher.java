package edu.kpi.testcourse.rest;

import io.reactivex.Single;

public interface UsernameFetcher {
  Single<String> findUsername();
}
