package com.scmspain.repository;

import java.util.List;

public interface TweetRepository<T, U> {
  public void create(T tweet);
  public void delete(T tweet);

  public T get(U id);
  public List<T> getAll();
}
