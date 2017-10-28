package android.location;

/**
 * Way Lin, 20171024.
 * <p>
 * The API for detecting the country where the user is.
 * <p>
 * {@hide}
 */

interface ICountryDetector {
    /**
     * Start detecting the country that the user is in.
     *
     * @return the country if it is available immediately, otherwise null will be returned.
     */
    Country detectCountry();

    /**
     * Add a listener to receive the notification when the country is detected or changed.
     */
    void addCountryListener(ICountryListener listener);

    /**
     * Remove the listener
     */
    void removeCountryListener(ICountryListener listener);
}
