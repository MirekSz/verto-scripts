package tools;

/**
 * Nazsluchuje zmiany danych o polaczeniu
 * 
 * @author Mirek
 * 
 */
public interface ConnectionDataChangeListener {

	void onChange(final String login, final String password,
			final String appLocation);

}
