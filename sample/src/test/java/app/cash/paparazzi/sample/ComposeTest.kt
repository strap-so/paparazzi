package app.cash.paparazzi.sample

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.ViewTreeSavedStateRegistryOwner
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class ComposeTest {
  @get:Rule
  val paparazzi = Paparazzi()

  @Test
  fun compose() {
    val view = ComposeView(paparazzi.context)
    PaparazziComposeOwner.register(view)
    view.setContent { HelloWorld() }
    paparazzi.snapshot(view)
  }
}

@Composable
fun HelloWorld(text: String = "Paparazzi") {
  Column {
    Text(text)
    Text(text, style = TextStyle(fontFamily = FontFamily.Cursive))
    Text(
        text = text,
        style = TextStyle(textDecoration = TextDecoration.LineThrough)
    )
    Text(
        text = text,
        style = TextStyle(textDecoration = TextDecoration.Underline)
    )
    Text(
        text = text,
        style = TextStyle(
            textDecoration = TextDecoration.combine(
                listOf(
                    TextDecoration.Underline,
                    TextDecoration.LineThrough
                )
            ),
            fontWeight = FontWeight.Bold
        )
    )
  }
}

class PaparazziComposeOwner private constructor() : LifecycleOwner, SavedStateRegistryOwner {
  private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
  private val savedStateRegistryController = SavedStateRegistryController.create(this)

  override fun getLifecycle(): Lifecycle = lifecycleRegistry
  override fun getSavedStateRegistry(): SavedStateRegistry = savedStateRegistryController.savedStateRegistry

  companion object {
    fun register(view: View) {
      val owner = PaparazziComposeOwner()
      ViewTreeLifecycleOwner.set(view, owner)
      ViewTreeSavedStateRegistryOwner.set(view, owner)
    }
  }
}