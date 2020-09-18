package id.org.test.common.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import id.org.test.common.constant.AppConstant;
import id.org.test.common.util.MultilangUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public abstract class BaseController {

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private ApplicationContext appCtx;

	// Administrator
	public static final String SYSTEM = AppConstant.UserRole.SYSTEM;
	public static final String ADMIN = AppConstant.UserRole.ADMIN;
	// Account
	public static final String MEMBER = AppConstant.UserRole.MEMBER;


	public static final class RedirectPage {
		public static final String FORBIDDEN = "redirect:/403";
		public static final String NOT_FOUND = "redirect:/404";
		public static final String UPGRADE_PRO = "error/redirect-billing";
		public static final String INVALID_REGISTRATION_URL = "registration/errorPage";
	}

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final int MAX_SIZE_PER_PAGE = 100;

	public static final String PAGE_PARAM_NOTES = "the value is absolute (non negative)";
	public static final String SIZE_PARAM_NOTES = "max value is 100";

	public static final int BOOLEAN_FALSE = 0;
	public static final int BOOLEAN_TRUE = 1;

	@Getter(value = AccessLevel.PRIVATE)
	private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

	@Setter(value = AccessLevel.PRIVATE)
	@Getter(value = AccessLevel.PRIVATE)
	private Matcher matcher;

	@Setter(value = AccessLevel.PRIVATE)
	@Getter(value = AccessLevel.PRIVATE)
	private ResponseEntity<Object> response = null;


	// REST API Response
	public ResponseEntity<Object> buildResponseGeneralSuccess() {
		return buildResponseGeneralSuccess(null);
	}

	public ResponseEntity<Object> buildResponseGeneralError() {
		return buildResponseGeneralError(null);
	}

	public ResponseEntity<Object> buildResponseGeneralSuccess(Object data) {
		setResponse(
				new ResponseEntity<Object>(ResponseWrapper.build(data, ResponseStatus.GENERAL_SUCCESS), HttpStatus.OK));
		return this.response;
	}

	public ResponseEntity<Object> unauthorized() {
		return buildResponse(null, ResponseStatus.AUTHORIZATION_FAIL, HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<Object> unauthorized(Object data) {
		return buildResponse(data, ResponseStatus.AUTHORIZATION_FAIL, HttpStatus.UNAUTHORIZED);
	}

	public ResponseEntity<Object> ok(Object data) {
		return buildResponseGeneralSuccess(data);
	}

	public ResponseEntity<Object> buildResponseGeneralError(Object data) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(data, ResponseStatus.GENERAL_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR));
		return this.response;
	}

	public ResponseEntity<Object> internalServerError(Object data) {
		return buildResponseGeneralError(data);
	}

	public ResponseEntity<Object> buildResponse(Object data, ResponseStatus responseStatus, HttpStatus httpStatus) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(data, responseStatus), httpStatus));
		return this.response;
	}

	public ResponseEntity<Object> buildResponse(ResponseStatus responseStatus, HttpStatus httpStatus) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(null, responseStatus), httpStatus));
		return this.response;
	}

	public ResponseEntity<Object> buildResponse(Object data, String code, String message, HttpStatus httpStatus) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(data, code, message), httpStatus));
		return this.response;
	}

	public ResponseEntity<Object> buildResponseProcessing(ResponseStatus responseStatus) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(null, responseStatus), HttpStatus.ACCEPTED));
		return this.response;
	}

	public ResponseEntity<Object> buildResponseNotFound() {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(null, ResponseStatus.GENERAL_NOT_FOUND),
				HttpStatus.NOT_FOUND));
		return this.response;
	}

	public ResponseEntity<Object> buildResponseNotFound(Object data) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(data, ResponseStatus.GENERAL_NOT_FOUND),
				HttpStatus.NOT_FOUND));
		return this.response;
	}

	public ResponseEntity<Object> buildResponseNotFound(ResponseStatus responseStatus) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(null, responseStatus), HttpStatus.NOT_FOUND));
		return this.response;
	}

	public ResponseEntity<Object> buildResponseBadRequest(Object data) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(data, ResponseStatus.GENERAL_WARN),
				HttpStatus.BAD_REQUEST));
		return this.response;
	}

	public ResponseEntity<Object> badRequest(Object data) {
		return buildResponseBadRequest(data);
	}

	public ResponseEntity<Object> notModified(Object data) {
		setResponse(new ResponseEntity<Object>(ResponseWrapper.build(data, ResponseStatus.GENERAL_WARN),
				HttpStatus.NOT_MODIFIED));
		return this.response;
	}

	public ResponseEntity<Object> found(Object data) {
		setResponse(
				new ResponseEntity<Object>(ResponseWrapper.build(data, ResponseStatus.GENERAL_INFO), HttpStatus.FOUND));
		return this.response;
	}

	public ResponseEntity<Object> notFound(Object data) {
		return buildResponseNotFound(data);
	}


	public boolean validEmail(final String email) {
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public int safetifyPage(int page) {
		return Math.abs(page);
	}

	public int safetifySize(int size) {
		if (size < 0)
			return Math.abs(size);

		if (size > MAX_SIZE_PER_PAGE)
			return MAX_SIZE_PER_PAGE;

		return size;
	}

	@Deprecated
	public String likely(String keyword) {
		return String.format("%s%s%s", "%", keyword, "%");
	}

	public static final DateFormat DATA_TABLE_DATE_FMT = new SimpleDateFormat("dd MMM yyyy");

	public Date fromDataTableToDate(String datePickerString) {
		Date result = null;
		try {
			result = DATA_TABLE_DATE_FMT.parse(datePickerString);
		} catch (ParseException e) {
			log.error("", e);
		}
		return result;
	}

}
