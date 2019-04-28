package com.group4.smartaccess;

import android.nfc.NdefMessage;

public interface SendNDEF {
    void sendMessage(NdefMessage output);
}
