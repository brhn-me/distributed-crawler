package com.brhn.me.distributedcrawler;

import java.net.InetAddress;

public class Util {
    public static String getHostAddress() {
        try {
            // Attempt to find the local host address
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            // Fallback to localhost if unable to determine the host address
            return "localhost";
        }
    }
}
