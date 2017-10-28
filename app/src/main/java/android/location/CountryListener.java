package android.location;

/**
 * Way Lin, 20171024.
 * <p>
 * The listener for receiving the notification when the country is detected or
 * changed
 *
 * @hide
 */

public interface CountryListener {
    /**
     * @param country the changed or detected country.
     */
    void onCountryDetected(Country country);
}
