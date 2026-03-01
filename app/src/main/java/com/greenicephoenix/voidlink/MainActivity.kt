package com.greenicephoenix.voidlink

import android.app.role.RoleManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.greenicephoenix.voidlink.ui.theme.PureBlack
import com.greenicephoenix.voidlink.ui.theme.SignatureRed
import com.greenicephoenix.voidlink.ui.theme.StarkWhite
import com.greenicephoenix.voidlink.ui.theme.VoidLinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoidLinkTheme {
                // 1. Observe the current dialer role status
                var hasDialerRole by remember { mutableStateOf(checkDialerRole()) }

                // 2. Create a launcher to handle the system prompt's result
                val roleLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) {
                    // 3. When the system prompt closes, re-check the role and update the state.
                    // If it's true, Compose will automatically instantly redraw the UI!
                    hasDialerRole = checkDialerRole()
                }

                if (hasDialerRole) {
                    // Success Screen
                    Surface(modifier = Modifier.fillMaxSize(), color = PureBlack) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("Dialpad UI goes here (Phase 4)", color = StarkWhite)
                        }
                    }
                } else {
                    // Permission Screen
                    PermissionScreen(
                        onGrantClick = {
                            val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
                            val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                            roleLauncher.launch(intent)
                        }
                    )
                }
            }
        }
    }

    /**
     * Checks if the Android OS currently considers Void Link as the default dialer.
     */
    private fun checkDialerRole(): Boolean {
        val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
        return roleManager.isRoleHeld(RoleManager.ROLE_DIALER)
    }
}

/**
 * Our 'Nothing' inspired permission screen.
 * It simply takes a click action and passes it up to the Activity.
 */
@Composable
fun PermissionScreen(onGrantClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PureBlack)
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Warning Dot
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(SignatureRed)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "SYSTEM OVERRIDE REQUIRED",
            style = MaterialTheme.typography.headlineMedium,
            color = StarkWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Void Link requires the Default Dialer role to securely handle your calls without third-party interference.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onGrantClick, // Triggers the launcher defined in MainActivity
            colors = ButtonDefaults.buttonColors(
                containerColor = StarkWhite,
                contentColor = PureBlack
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp)
        ) {
            Text(
                text = "GRANT ROLE",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}