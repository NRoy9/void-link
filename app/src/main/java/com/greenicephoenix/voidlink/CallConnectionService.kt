package com.greenicephoenix.voidlink

import android.telecom.InCallService

/**
 * Phase 5 Placeholder: Android OS requires any app requesting ROLE_DIALER
 * to have a registered InCallService.
 * * Later, this class will be responsible for listening to incoming calls,
 * outgoing calls, and updating our custom "Nothing" styled call screen.
 */
class CallConnectionService : InCallService() {

    // We will override functions like onCallAdded() and onCallRemoved() here later.
    // For now, simply existing is enough to satisfy the RoleManager.

}