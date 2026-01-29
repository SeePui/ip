package pie.exception;

/**
 * Represents an exception that occurs during storage-related operations.
 *
 * <p>
 * This exception is thrown when the application fails to load data from
 * or save data to storage.
 * </p>
 */
public class StorageException extends Exception {
    /**
     * Instantiates a new {@code StorageException} with a specific error
     * message describing the storage failure.
     *
     * @param description A detailed explanation of the storage error.
     */
    public StorageException(String description) {
        super(description);
    }
}
