package org.ldemetrios.simulator

import kotlinx.collections.immutable.PersistentList
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

class Canvas : JPanel() {
    override fun getPreferredSize(): Dimension = Dimension(SCREEN_WIDTH*2, SCREEN_HEIGHT*2)
    override fun getMinimumSize(): Dimension = preferredSize
    var screen: PersistentList<Int> = EMPTY_SCREEN

    override fun paint(g: Graphics) {
        g.color = Color(0)
        g.fillRect(0, 0, SCREEN_WIDTH*2, SCREEN_HEIGHT*2)
        for (x in 0 until SCREEN_WIDTH) for (y in 0 until SCREEN_HEIGHT) {
            val it = screen[y * SCREEN_WIDTH + x]
            if (it != 0) {
                g.color = Color(it)
                g.fillRect(x * 2, y * 2, 2, 2)
            }
        }
    }
}

class CanvasFrame : JFrame("display") {
    private val canvas = Canvas()
    var screen: PersistentList<Int>
        get() = canvas.screen
        set(value) {
            canvas.screen = value
            repaint()
        }

    init {
        layout = FlowLayout()
        add(canvas)
        pack()
        isVisible = true
    }
}