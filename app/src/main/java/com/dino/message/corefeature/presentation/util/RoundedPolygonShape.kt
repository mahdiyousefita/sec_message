import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import androidx.graphics.shapes.toPath as toAndroidPath

// Custom Shape class to wrap a RoundedPolygon
class RoundedPolygonShape(private val roundedPolygon: RoundedPolygon) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        // You might need to adjust the polygon's size/position based on the 'size' parameter
        // for optimal fit within the composable. For a simple pill, this often involves
        // scaling the polygon to fit the provided size.
        // The MaterialShapes.Pill is defined with a unit coordinate system (0 to 1).
        // So, we scale it to fit the actual composable size.
        val scaledPath: Path = roundedPolygon
            .toPath() // Converts RoundedPolygon to android.graphics.Path
            .asComposePath() // Converts android.graphics.Path to androidx.compose.ui.graphics.Path

        // Optional: Scale the path to fit the given 'size'
        val pathBounds = scaledPath.getBounds()
        val scaleX = size.width / pathBounds.width
        val scaleY = size.height / pathBounds.height

        val scaledAndTranslatedPath = Path().apply {
            addPath(scaledPath, androidx.compose.ui.geometry.Offset(
                -pathBounds.left * scaleX, // Move to 0,0 relative to its scaled bounds
                -pathBounds.top * scaleY
            ))
            // Now apply the scale
            transform(androidx.compose.ui.graphics.Matrix().apply {
                scale(scaleX, scaleY)
            })
        }

        return Outline.Generic(scaledAndTranslatedPath)
    }
}