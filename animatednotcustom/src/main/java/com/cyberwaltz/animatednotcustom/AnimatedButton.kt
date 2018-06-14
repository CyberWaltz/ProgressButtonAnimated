package com.cyberwaltz.animatednotcustom

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.dd.processbutton.iml.SubmitProcessButton
import java.util.*

/**
 * Created by dmitrij on 14.06.18.
 */
class AnimatedButton : LinearLayout, View.OnTouchListener {
    lateinit var progressBar: ProgressBar
    lateinit var button: SubmitProcessButton
    private var timer: Timer? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    super(context, attrs, defStyleAttr) {
        init()

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.progressbar_under_button, this)

        button = findViewById(R.id.button_from_lib)
        progressBar = findViewById(R.id.progress_bar)

        button.setOnTouchListener(this)
    }

    internal val handler = Handler(context.mainLooper)
    private var touchingMillis = 0L

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            touchingMillis = System.currentTimeMillis()
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                var i = 0
                var cfs = 5
                override fun run() {
                    if (i >= 19) {
                        timer?.cancel()
                        timer = null

                        handler.post({
                            button.progress = 100
                            setButtonVisibility(false)
                        })
                        return
                    }

                    handler.post({
                        button.progress = this.cfs
                    })
                    i += 1
                    cfs += 5
                }
            }, 150, 150)
        } else if (event?.action == MotionEvent.ACTION_UP) {
            timer?.cancel()
            timer = null
            button.progress = 0
        }
        return true
    }

    fun setButtonText(text: String) {
        button.text = text
    }

    fun setButtonVisibility(visible: Boolean) {
        button.visibility = if (visible) VISIBLE else GONE
        progressBar.visibility = if (visible) GONE else VISIBLE
    }
}