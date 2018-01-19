package pmc.bll;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class BLLException extends Exception
{
    /**
     * Exception by message
     * @param message
     */
    public BLLException(String message)
    {
        super(message);
    }

    /**
     * Exception by message and cause.
     * @param message
     * @param cause
     */
    public BLLException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Exception by cause.
     * @param cause
     */
    public BLLException(Throwable cause)
    {
        super(cause);
    }
}
