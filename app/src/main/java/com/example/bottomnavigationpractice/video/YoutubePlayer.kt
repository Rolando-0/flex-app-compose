/**
 * A kotlin file with a composable function that
 * can be used to play videos inside screens.
 *
 * */


package com.example.bottomnavigationpractice.video

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.regex.Pattern



@Composable
fun YoutubePlayer(youtubeVideoUrl: String, lifecycleOwner: LifecycleOwner, modifier: Modifier) {

    val youtubeVideoID = getYouTubeVideoId(youtubeVideoUrl)
    if(youtubeVideoID != null) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(10.dp)),
            factory = {
                YouTubePlayerView(context = it).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(youtubeVideoID, 0f)
                        }
                    }
                    )
                }
            }
        )
    }
    else{
        Text("Video not found/ Invalid URL")
    }

}

fun getYouTubeVideoId(url: String): String? {
    val videoIdPattern = listOf(
        "v=([a-zA-Z0-9_-]{11})", // Standard URL (e.g., https://www.youtube.com/watch?v=VIDEO_ID)
        "youtu\\.be/([a-zA-Z0-9_-]{11})", // Shortened URL (e.g., https://youtu.be/VIDEO_ID)
        "embed/([a-zA-Z0-9_-]{11})", // Embedded URL (e.g., https://www.youtube.com/embed/VIDEO_ID)
        "v/([a-zA-Z0-9_-]{11})", // Another embedded URL format (e.g., https://www.youtube.com/v/VIDEO_ID)
        "shorts/([a-zA-Z0-9_-]{11})", // Shorts URL (e.g., https://www.youtube.com/shorts/VIDEO_ID)
    )

    for (pattern in videoIdPattern) {
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(url)
        if (matcher.find()) {
            return matcher.group(1)
        }
    }
    return null
}
