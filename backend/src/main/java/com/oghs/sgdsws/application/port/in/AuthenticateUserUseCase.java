package com.oghs.sgdsws.application.port.in;

import com.oghs.sgdsws.application.port.in.command.AuthenticateUserCommand;
import com.oghs.sgdsws.application.port.in.result.AuthenticationResult;

public interface AuthenticateUserUseCase {
    AuthenticationResult authenticate(AuthenticateUserCommand command);
}
