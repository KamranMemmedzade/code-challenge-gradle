package com.bestcommerce.service;

public interface LoginService {

  Long currentLoggedInUserId();

  String authenticate(String username, String password, boolean rememberMe);

}
