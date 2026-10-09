package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sin
import kotlin.math.PI
import kotlin.math.exp

class MysteryAudioEngine(private val context: Context) {

  private val scope = CoroutineScope(Dispatchers.Default)

  fun triggerBlackoutCue() {
    vibratePattern(longArrayOf(0, 150, 80, 200, 100, 500))
    scope.launch {
      // Play dramatic thunder crash: low rumbling brown noise + thud
      playSynthesizedSound(sampleRate = 44100, durationMs = 1200) { sampleIndex, totalSamples ->
        val progress = sampleIndex.toDouble() / totalSamples
        val env = (1.0 - progress) * (1.0 - progress)
        val rumble = sin(2.0 * PI * 65.0 * sampleIndex / 44100) * 0.6 +
                     sin(2.0 * PI * 42.0 * sampleIndex / 44100) * 0.4
        // Add crackle noise
        val noise = (Math.random() * 2.0 - 1.0) * 0.3 * exp(-progress * 4.0)
        (rumble * 0.7 + noise) * env
      }
    }
  }

  fun triggerDramaticThud() {
    vibratePattern(longArrayOf(0, 300, 100, 250))
    scope.launch {
      playSynthesizedSound(sampleRate = 44100, durationMs = 800) { sampleIndex, totalSamples ->
        val progress = sampleIndex.toDouble() / totalSamples
        val env = exp(-progress * 5.0)
        // Pitch drops from 120Hz to 40Hz
        val freq = 120.0 * (1.0 - progress * 0.6)
        val wave = sin(2.0 * PI * freq * sampleIndex / 44100)
        wave * env
      }
    }
  }

  fun triggerClockTick() {
    vibratePattern(longArrayOf(0, 40))
    scope.launch {
      playSynthesizedSound(sampleRate = 44100, durationMs = 120) { sampleIndex, totalSamples ->
        val progress = sampleIndex.toDouble() / totalSamples
        val env = exp(-progress * 18.0)
        val wave = sin(2.0 * PI * 1800.0 * sampleIndex / 44100)
        wave * env
      }
    }
  }

  fun triggerClueDiscoveredSting() {
    vibratePattern(longArrayOf(0, 80, 50, 120))
    scope.launch {
      // Mystery chord: E minor mysterious chime
      playSynthesizedSound(sampleRate = 44100, durationMs = 900) { sampleIndex, totalSamples ->
        val progress = sampleIndex.toDouble() / totalSamples
        val env = exp(-progress * 3.5)
        val e4 = sin(2.0 * PI * 329.63 * sampleIndex / 44100)
        val g4 = sin(2.0 * PI * 392.00 * sampleIndex / 44100)
        val b4 = sin(2.0 * PI * 493.88 * sampleIndex / 44100)
        val chime = (e4 + g4 + b4) / 3.0
        chime * env
      }
    }
  }

  fun triggerVerdictFanfare() {
    vibratePattern(longArrayOf(0, 200, 100, 200, 100, 600))
    scope.launch {
      // Dramatic minor-to-major revelation chord
      playSynthesizedSound(sampleRate = 44100, durationMs = 1500) { sampleIndex, totalSamples ->
        val progress = sampleIndex.toDouble() / totalSamples
        val env = if (progress < 0.1) progress / 0.1 else exp(-(progress - 0.1) * 2.5)
        val c3 = sin(2.0 * PI * 130.81 * sampleIndex / 44100)
        val g3 = sin(2.0 * PI * 196.00 * sampleIndex / 44100)
        val c4 = sin(2.0 * PI * 261.63 * sampleIndex / 44100)
        val eb4 = sin(2.0 * PI * 311.13 * sampleIndex / 44100)
        val tone = (c3 * 0.4 + g3 * 0.3 + c4 * 0.2 + eb4 * 0.2)
        tone * env
      }
    }
  }

  private fun playSynthesizedSound(
    sampleRate: Int,
    durationMs: Int,
    generator: (sampleIndex: Int, totalSamples: Int) -> Double
  ) {
    try {
      val totalSamples = (sampleRate * (durationMs / 1000.0)).toInt()
      val buffer = ShortArray(totalSamples)
      for (i in 0 until totalSamples) {
        val sampleVal = generator(i, totalSamples).coerceIn(-1.0, 1.0)
        buffer[i] = (sampleVal * Short.MAX_VALUE).toInt().toShort()
      }

      val audioTrack = AudioTrack.Builder()
        .setAudioAttributes(
          AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        )
        .setAudioFormat(
          AudioFormat.Builder()
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(sampleRate)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        )
        .setBufferSizeInBytes(buffer.size * 2)
        .setTransferMode(AudioTrack.MODE_STATIC)
        .build()

      audioTrack.write(buffer, 0, buffer.size)
      audioTrack.play()
    } catch (_: Exception) {
      // Fallback silently if audio focus is unavailable
    }
  }

  private fun vibratePattern(pattern: LongArray) {
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        val vibrator = vibratorManager?.defaultVibrator
        vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
      } else {
        @Suppress("DEPRECATION")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          vibrator?.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
          @Suppress("DEPRECATION")
          vibrator?.vibrate(pattern, -1)
        }
      }
    } catch (_: Exception) {
      // Ignore vibration error
    }
  }
}
