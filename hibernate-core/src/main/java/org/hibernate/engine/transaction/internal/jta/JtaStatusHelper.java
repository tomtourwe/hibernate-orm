package org.hibernate.engine.transaction.internal.jta;

import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;

import org.hibernate.TransactionException;

import static jakarta.transaction.Status.STATUS_ACTIVE;
import static jakarta.transaction.Status.STATUS_COMMITTED;
import static jakarta.transaction.Status.STATUS_MARKED_ROLLBACK;
import static jakarta.transaction.Status.STATUS_ROLLEDBACK;
import static jakarta.transaction.Status.STATUS_ROLLING_BACK;
import static jakarta.transaction.Status.STATUS_UNKNOWN;

/**
 * Utility for dealing with JTA statuses.
 *
 * @author Steve Ebersole
 */
public final class JtaStatusHelper {
	private JtaStatusHelper() {
	}

	/**
	 * Extract the status code from the current {@link jakarta.transaction.Transaction} associated with the
	 * given {@link TransactionManager}
	 *
	 * @param transactionManager The {@link TransactionManager} from which to extract the status.
	 *
	 * @return The transaction status
	 *
	 * @throws TransactionException If the {@link TransactionManager} reports the status as unknown
	 */
	public static int getStatus(TransactionManager transactionManager) {
		try {
			final int status = transactionManager.getStatus();
			if ( status == STATUS_UNKNOWN ) {
				throw new TransactionException( "TransactionManager reported transaction status as unknwon" );
			}
			return status;
		}
		catch ( SystemException se ) {
			throw new TransactionException( "Could not determine transaction status", se );
		}
	}

	/**
	 * Does the given status code indicate an active transaction?
	 *
	 * @param status The transaction status code to check
	 *
	 * @return True if the code indicates active; false otherwise.
	 */
	public static boolean isActive(int status) {
		return status == STATUS_ACTIVE;
	}

	/**
	 * Does the status code obtained from the given {@link TransactionManager} indicate an active transaction?
	 *
	 * @param transactionManager The {@link TransactionManager} whose status is to be checked
	 *
	 * @return True if the transaction is active; false otherwise.
	 */
	public static boolean isActive(TransactionManager transactionManager) {
		return isActive( getStatus( transactionManager ) );
	}

	/**
	 * Does the given status code indicate a rolled back transaction?
	 *
	 * @param status The transaction status code to check
	 *
	 * @return True if the code indicates a roll back; false otherwise.
	 */
	public static boolean isRollback(int status) {
		return status == STATUS_MARKED_ROLLBACK
			|| status == STATUS_ROLLING_BACK
			|| status == STATUS_ROLLEDBACK;
	}

	/**
	 * Does the given status code indicate a committed transaction?
	 *
	 * @param status The transaction status code to check
	 *
	 * @return True if the code indicates a roll back; false otherwise.
	 */
	public static boolean isCommitted(int status) {
		return status == STATUS_COMMITTED;
	}

	/**
	 * Does the given status code indicate the transaction has been marked for rollback?
	 *
	 * @param status The transaction status code to check
	 *
	 * @return True if the code indicates a roll back; false otherwise.
	 */
	@SuppressWarnings("unused")
	public static boolean isMarkedForRollback(int status) {
		return status == STATUS_MARKED_ROLLBACK;
	}
}
