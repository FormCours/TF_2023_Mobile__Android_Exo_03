package be.tftic.web2023.exo03.tools

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import be.tftic.web2023.exo03.R
import com.kevincodes.recyclerview.ItemDecorator

class SwipeTool(val context: Context) : ItemTouchHelper.Callback() {

    // region Listener "onSwipe" sur un element
    fun interface OnSwipeLeftListener {
        fun onSwipeLeft(position: Int)
    }
    fun interface OnSwipeRightListener {
        fun onSwipeRight(position: Int)
    }

    private var onSwipeLeftListener : OnSwipeLeftListener? = null
    private var onSwipeRightListener : OnSwipeRightListener? = null

    fun setOnSwipeLeftListener(listener: OnSwipeLeftListener) {
        onSwipeLeftListener = listener
    }
    fun setOnSwipeRightListener(listener: OnSwipeRightListener) {
        onSwipeRightListener = listener
    }
    // endregion



    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // Orientation du Swipe autorisé (Gauche + Droite)
        // return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        // Orientation du Swipe autorisé (Basé sur les listener)
        var movementFlag = 0

        if(onSwipeLeftListener != null) {
            movementFlag = movementFlag or ItemTouchHelper.LEFT
        }
        if(onSwipeRightListener != null) {
            movementFlag = movementFlag or ItemTouchHelper.RIGHT
        }

        return makeMovementFlags(0, movementFlag)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val position : Int = viewHolder.layoutPosition
        Log.d("MELVIN", "Position: $position")

        when (direction){
            ItemTouchHelper.LEFT -> {
                Log.d("JAWAD", "Direction LEFT ")
                onSwipeLeftListener?.onSwipeLeft(position)
            }
            ItemTouchHelper.RIGHT -> {
                Log.d("JAWAD", "Direction RIGHT ")
                onSwipeRightListener?.onSwipeRight(position)
            }
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        val green = context.getColor(R.color.green)
        val red = context.getColor(R.color.red)
        val defaultWhiteColor = context.getColor(R.color.white)

        ItemDecorator.Builder(c, recyclerView, viewHolder, dX, actionState).set(
            backgroundColorFromStartToEnd = green,
            backgroundColorFromEndToStart = red,
            textFromStartToEnd = "Editer",
            textFromEndToStart = "Supprimer",
            textColorFromStartToEnd = defaultWhiteColor,
            textColorFromEndToStart = defaultWhiteColor,
            iconTintColorFromStartToEnd = defaultWhiteColor,
            iconTintColorFromEndToStart = defaultWhiteColor,
            textSizeFromStartToEnd = 16f,
            textSizeFromEndToStart = 16f,
            typeFaceFromStartToEnd = Typeface.DEFAULT_BOLD,
            typeFaceFromEndToStart = Typeface.SANS_SERIF,
//            iconResIdFromStartToEnd = R.drawable.ic_baseline_delete_24,
//            iconResIdFromEndToStart = R.drawable.ic_baseline_done_24
        )

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }



//    private var swipeBack : Boolean = false
//
//    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
//        if(swipeBack) {
//            swipeBack = false
//            return 0
//        }
//        return super.convertToAbsoluteDirection(flags, layoutDirection)
//    }
//
//    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//
//        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            setSwapeTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//        }
//
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//    }
//
//    private fun setSwapeTouchListener(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//
//        recyclerView.setOnTouchListener { view, event ->
//            swipeBack = (event.action == MotionEvent.ACTION_CANCEL)
//                    || (event.action == MotionEvent.ACTION_UP)
//
//            Log.d("MELVIN", "swipeBack: " + swipeBack)
//            Log.d("MELVIN", "event.action: " + event.action)
//
//            false
//        }
//    }

}