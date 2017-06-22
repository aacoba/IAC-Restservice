package net.aacoba.iac.parkeerplaats.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class NoParkingSpotsAvailableException extends RuntimeException {
}