package app.hydroshen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private var selectedConfig by mutableStateOf<String?>(null)
    private val configPicker = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            runCatching { contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION) }
            selectedConfig = it.lastPathSegment?.substringAfterLast('/') ?: "Configuration selected"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HydroshenScreen(
                    configName = selectedConfig,
                    onPickConfig = { configPicker.launch(arrayOf("application/json", "text/plain", "*/*")) }
                )
            }
        }
    }
}

@Composable
private fun HydroshenScreen(configName: String?, onPickConfig: () -> Unit) {
    val ink = Color(0xFF101827)
    val blue = Color(0xFF247BFF)
    Surface(Modifier.fillMaxSize(), color = Color(0xFFF4F7FB)) {
        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier.fillMaxWidth().background(
                    Brush.linearGradient(listOf(Color(0xFF101827), Color(0xFF153E75)))
                ).padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Security, contentDescription = null, tint = Color(0xFF72B7FF))
                    Text("  HYDROSHEN", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                }
                Spacer(Modifier.height(12.dp))
                Text("Your connection. Your control.", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.SemiBold)
                Text("Android VPN workspace", color = Color(0xFFBED7F8), fontSize = 14.sp)
            }
            Column(
                Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Security, contentDescription = null, tint = blue)
                            Text("  VPN status", fontWeight = FontWeight.Bold, color = ink, fontSize = 18.sp)
                        }
                        Text("Engine not connected", color = Color(0xFFB45309), fontWeight = FontWeight.SemiBold)
                        Text(
                            "The Android interface is ready. The original Windows VPN core still needs an Android-compatible port before a secure tunnel can be started.",
                            color = Color(0xFF586579), fontSize = 14.sp, lineHeight = 20.sp
                        )
                        HorizontalDivider(color = Color(0xFFE7ECF3))
                        Text("Profile", color = Color(0xFF64748B), fontSize = 12.sp)
                        Text(configName ?: "No configuration selected", color = ink, fontWeight = FontWeight.Medium)
                        OutlinedButton(onClick = onPickConfig, modifier = Modifier.fillMaxWidth()) {
                            Icon(Icons.Outlined.CloudDownload, contentDescription = null)
                            Text("  Import configuration")
                        }
                        Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                            Text("Connect (VPN engine pending)")
                        }
                    }
                }
                Card(
                    Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Tune, contentDescription = null, tint = blue)
                        Column(Modifier.padding(start = 12.dp)) {
                            Text("Built for Android", color = ink, fontWeight = FontWeight.Bold)
                            Text("Native interface • Gradle • CI APK builds", color = Color(0xFF64748B), fontSize = 13.sp)
                        }
                    }
                }
                Spacer(Modifier.weight(1f))
                Text("Developed by SHΞЯVIN™", Modifier.align(Alignment.CenterHorizontally), color = Color(0xFF64748B), fontSize = 12.sp)
            }
        }
    }
}
