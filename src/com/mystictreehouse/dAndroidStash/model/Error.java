package com.mystictreehouse.dAndroidStash.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: tannalein
 * Date: 4/9/13
 * Time: 5:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class Error {
    private String status;
    private String error;
    private String errorDescription;

    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String ERROR_DESCRIPTION = "error_description";

    public Error() {}

    public Error(JSONObject json) throws JSONException {
        this.status = json.getString(STATUS);
        this.error = json.getString(ERROR);
        this.errorDescription = json.getString(ERROR_DESCRIPTION);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }
}
