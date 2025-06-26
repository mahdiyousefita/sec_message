import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Matrix
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath as toAndroidPath

class RoundedPolygonShape(private val polygon: RoundedPolygon) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val polygonPath = polygon.toAndroidPath().asComposePath()

        // Get bounds of the original shape
        val bounds = polygonPath.getBounds()
        val scaleX = size.width / bounds.width
        val scaleY = size.height / bounds.height

        // Apply matrix scale and translate
        val matrix = Matrix()
        matrix.translate(-bounds.left, -bounds.top) // move to origin first
        matrix.scale(scaleX, scaleY)

        polygonPath.transform(matrix)

        return Outline.Generic(polygonPath)
    }
}
