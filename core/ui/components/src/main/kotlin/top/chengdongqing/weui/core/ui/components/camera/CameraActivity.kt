package top.chengdongqing.weui.core.ui.components.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import top.chengdongqing.weui.core.data.model.VisualMediaType
import top.chengdongqing.weui.core.ui.theme.WeUITheme

class CameraActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getStringExtra("type")?.run { VisualMediaType.valueOf(this) }
            ?: VisualMediaType.IMAGE_AND_VIDEO

        setContent {
            WeUITheme {
                WeCamera(type, onRevoked = { finish() }) { uri, type ->
                    val intent = Intent().apply {
                        putExtra("uri", uri)
                        putExtra("type", type)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, CameraActivity::class.java)
    }
}

@Composable
fun rememberCameraLauncher(): Pair<(VisualMediaType) -> Unit, Uri?> {
    val context = LocalContext.current
    var uri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra("uri", Uri::class.java)
            } else {
                @Suppress("DEPRECATION")
                result.data?.getParcelableExtra("uri")
            }
            uri = resultUri
        }
    }

    val startCamera = { type: VisualMediaType ->
        val intent = CameraActivity.newIntent(context).apply {
            putExtra("type", type.toString())
        }
        launcher.launch(intent)
    }

    return Pair(startCamera, uri)
}