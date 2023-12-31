package com.storm.score.com.exception;
/**
 *
 */

import com.storm.score.com.exception.api.BadRequestException;
import com.storm.score.com.exception.api.ConflictException;
import com.storm.score.com.exception.api.EmptyFileException;
import com.storm.score.com.exception.api.InternalServerErrorException;
import com.storm.score.com.exception.api.MaxUploadSizeExceededException;
import com.storm.score.com.exception.api.NotModifiedException;
import com.storm.score.com.exception.api.UnauthorizedException;

/**
 * description    :
 * packageName    : com.storm.score.com.exception
 * fileName       : ExceptionFactory
 * author         : wammelier
 * date           : 2023/12/10
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/12/10        wammelier       최초 생성
 */
public interface ExceptionFactory {

  static  ApiException getException(final ErrorCode errorCode) {
    switch (errorCode) {
        case E001:
          return new NotModifiedException(errorCode.getCode());
        case E401:
        case E402:
          return new BadRequestException(errorCode.getCode());
        case E403:
        case E404:
          return new UnauthorizedException(errorCode.getCode());
        case E406:
          return new EmptyFileException(errorCode.getCode());
        case E409:
          return new ConflictException(errorCode.getCode());
        case E501:
        case E502:
        case E503:
        case E504:

          return new InternalServerErrorException(errorCode.getCode());
        case E505:
          return new MaxUploadSizeExceededException(errorCode.getCode());
        default:
          return new InternalServerErrorException(ErrorCode.E504.getCode());
      }
}
}