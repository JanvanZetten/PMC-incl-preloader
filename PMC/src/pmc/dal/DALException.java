package pmc.dal;

/**
 * En Gruppe
 * @author Alex, Asbjørn & Jan
 */
public class DALException extends Exception
{
    /**
     * Exception by message
     * @param message
     */
    public DALException(String message)
    {
        super(message);
    }

    /**
     * Exception by message and cause.
     * @param message
     * @param cause
     */
    public DALException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Exception by cause.
     * @param cause
     */
    public DALException(Throwable cause)
    {
        super(cause);
    }
}
