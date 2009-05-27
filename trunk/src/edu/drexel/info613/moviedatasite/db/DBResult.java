package edu.drexel.info613.moviedatasite.db;

/*
 * This class will be a mechanism for communicating DB errors back to the caller. @author Patrick
 * Freestone, Batuhan Yukselen
 */
public class DBResult {
    private boolean success;
    private String  error = null;

    /**
     * Empty Constructor. Defaults error to empty string and success to false.
     */
    public DBResult() {
        this.success = false;
        this.error = "";
    }

    /**
     * Indicates the success of the operation
     * 
     * @return success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Mutator method for success attribute
     * 
     * @param wasSuccess
     */
    public void setSuccess(boolean wasSuccess) {
        this.success = wasSuccess;

        if (wasSuccess == true) {
            this.error = "";
        }
    }

    /**
     * Accessor method for error attribute
     * 
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * Mutator method for error attribute
     * 
     * @param error
     */
    public void setError(String error) {
        this.error = error;
    }
}