package com.scmspain.validators;

public interface Validator<T> {
  public void validate (T item) throws IllegalArgumentException;
}