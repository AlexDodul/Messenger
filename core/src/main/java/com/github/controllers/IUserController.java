package com.github.controllers;

import com.github.utils.http.Status;

public interface IUserController {

    Status authorize(String body);

    Status register(String body);

}
