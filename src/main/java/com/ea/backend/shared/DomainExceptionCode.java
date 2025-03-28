package com.ea.backend.shared;

import lombok.Getter;

@Getter
public enum DomainExceptionCode {
  DOMAIN_ERROR("DOMAIN_ERROR"),
  SPACE_ALREADY_EXISTS("SPACE_ALREADY_EXISTS"),
  SPACE_NOT_FOUND("SPACE_NOT_FOUND"),
  DUPLICATE_FOUND("DUPLICATE_FOUND");

  private final String code;

  DomainExceptionCode(String code) {
    this.code = code;
  }

}
