package app.cash.paparazzi.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import app.cash.paparazzi.DeviceConfig;
import app.cash.paparazzi.EnvironmentKt;
import app.cash.paparazzi.HtmlReportWriter;
import app.cash.paparazzi.Paparazzi;
import app.cash.paparazzi.RenderExtension;
import app.cash.paparazzi.SnapshotVerifier;
import com.android.ide.common.rendering.api.SessionParams;
import java.util.HashSet;
import org.junit.Rule;
import org.junit.Test;

public class JavaTest {
  @Rule
  Paparazzi paparazzi = new Paparazzi(
      EnvironmentKt.detectEnvironment(),
      DeviceConfig.NEXUS_5,
      "AppTheme.ScreenshotTest",
      SessionParams.RenderingMode.NORMAL,
      true,
      0.0,
      Boolean.parseBoolean(System.getProperty("paparazzi.test.verify"))
          ? new SnapshotVerifier(0.0)
          : new HtmlReportWriter(),
      new HashSet<RenderExtension>()
  );

  @Test public void snapshot() {
    Context context = paparazzi.getContext();
    LayoutInflater layoutInflater = paparazzi.getLayoutInflater();

    LinearLayout view = paparazzi.inflate(R.layout.launch);
    paparazzi.snapshot(view);
    paparazzi.gif(view);

    View view2 = layoutInflater.inflate(R.layout.launch, new LinearLayout(context));
    paparazzi.snapshot(view2);
    paparazzi.gif(view2);

    View view3 = new View(context);
    paparazzi.snapshot(view3);
    paparazzi.gif(view3);
  }
}
