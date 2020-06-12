package com.ruchajoshi.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSnapPositionChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val musicList= arrayListOf(R.drawable.album_cover1,R.drawable.album_cover2,R.drawable.album_cover3,R.drawable.album_cover4)

        recylerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recylerview.adapter = MusicAdapter(musicList)

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recylerview)

        val snapOnScrollListener = SnapOnScrollListener(
                snapHelper,
                SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE,
                this)
        recylerview.addOnScrollListener(snapOnScrollListener)

    }


    class SnapOnScrollListener(
        private val snapHelper: SnapHelper,
        var behavior: Behavior = Behavior.NOTIFY_ON_SCROLL,
        var onSnapPositionChangeListener: OnSnapPositionChangeListener? = null
    ) : RecyclerView.OnScrollListener() {

        enum class Behavior {
            NOTIFY_ON_SCROLL,
            NOTIFY_ON_SCROLL_STATE_IDLE
        }

        private var snapPosition = RecyclerView.NO_POSITION

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (behavior == Behavior.NOTIFY_ON_SCROLL) {
                maybeNotifySnapPositionChange(recyclerView)
            }

//            val layoutManager = recyclerView.layoutManager!!
//            val midpoint = layoutManager.width / 2f
//            val distance = 100 * midpoint //shrinkDistance=100
//
//            for (i in 0 until layoutManager.childCount) {
//                val child = layoutManager.getChildAt(i)!!
//                val childMidpoint = (layoutManager.getDecoratedRight(child) + layoutManager.getDecoratedLeft(child)) / 2f
//                val d = Math.min(distance, Math.abs(midpoint - childMidpoint))
//                val scale = 1 + -50 * d / distance //shrinkAmount =-50
//                child.scaleX = scale
//                child.scaleY = scale
//            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (behavior == Behavior.NOTIFY_ON_SCROLL_STATE_IDLE
                && newState == RecyclerView.SCROLL_STATE_IDLE
            ) {
                maybeNotifySnapPositionChange(recyclerView)
            }
        }

        private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
            val snapPosition = snapHelper.getSnapPosition(recyclerView)
            val snapPositionChanged = this.snapPosition != snapPosition
            if (snapPositionChanged) {
                onSnapPositionChangeListener?.onSnapPositionChange(snapPosition)
                this.snapPosition = snapPosition
            }
        }

        fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
            val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
            val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
            return layoutManager.getPosition(snapView)
        }
    }

    override fun onSnapPositionChange(position: Int) {
        Toast.makeText(this, "Position: $position", Toast.LENGTH_SHORT).show()
    }


}


interface OnSnapPositionChangeListener {
    fun onSnapPositionChange(position: Int)
}