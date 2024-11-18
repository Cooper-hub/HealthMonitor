package com.example.healthmonitor;

/**
 * This interface defines a callback mechanism for receiving monitor data.
 * Implementations of this interface will handle the data received from the monitor.
 */
public interface MonitorDataCallback {

    /**
     * Called when monitor data is received.
     *
     * @param value the monitor data as a string to be processed
     */
    void onCallback(String value);
}
