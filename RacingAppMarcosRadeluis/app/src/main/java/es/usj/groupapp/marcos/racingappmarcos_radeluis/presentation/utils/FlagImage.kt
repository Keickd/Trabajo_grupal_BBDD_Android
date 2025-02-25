package es.usj.groupapp.marcos.racingappmarcos_radeluis.presentation.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder

@Composable
fun FlagImage(flagPath: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()


    Image(
        painter = rememberAsyncImagePainter("file:///android_asset/$flagPath", imageLoader),
        contentDescription = null,
        modifier = modifier
    )
}