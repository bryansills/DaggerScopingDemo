package ninja.bryansills.daggerscopingdemo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CoolBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color.LightGray)
            .size(300.dp)
    ) {
        Text(
            text = "This text is on the left",
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Box(
            modifier = Modifier
                .background(Color.Cyan)
                .size(50.dp)
                .align(Alignment.TopCenter)
        )

        Text(
            text = "This text is on the right",
            color = Color.Magenta,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun CoolBox(customBackground: Color, modifier: Modifier = Modifier) {

}

@Preview
@Composable
fun CoolBoxPreview() {
    CoolBox()
}

@Preview
@Composable
fun CoolBoxPreviewTwo() {
    CoolBox(
        modifier = Modifier.border(2.dp, Color.Green)
    )
}


@Preview
@Composable
fun CoolBoxPreviewThree() {
    CoolBox(
        modifier = Modifier.size(400.dp)
    )
}
