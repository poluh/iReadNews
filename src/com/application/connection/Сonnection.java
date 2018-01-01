package com.application.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Сonnection {
    public boolean connected() {
        int timeout = 2000;
        InetAddress[] addresses = new InetAddress[0];
        try {
            addresses = InetAddress.getAllByName("www.google.com");
        } catch (UnknownHostException ignored) { }
        for (InetAddress address : addresses) {
            try {
                return address.isReachable(timeout);
            } catch (IOException ignored) { }
        }

        return false;
    }
}
