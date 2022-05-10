package com.erif.fragmentnavigationdemo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class CardViewVoucher : ConstraintLayout {

    private var srcRef: Int = 0
    private var title: String? = null
    private var subtitle: String? = null
    private lateinit var imageView: ImageView
    private lateinit var cardBottom: LinearLayout
    private lateinit var txtTitle: TextView
    private lateinit var txtSubtitle: TextView
    private val cornerRadius = 35f

    constructor(context: Context): super(context) {
        init(null)
    }

    constructor(
        context: Context, attrs: AttributeSet?
    ): super(context, attrs) {
        init(attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet?, defStyleAttr: Int
    ): super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        setWillNotDraw(false)
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        removeAllViews()
        clipToPadding = false
        val shadowRadius = 5.dp
        setPadding(
            shadowRadius,
            shadowRadius,
            shadowRadius,
            shadowRadius
        )
        val inflater = LayoutInflater.from(context)
        val viewImage = inflater.inflate(R.layout.layout_voucher_image, null, false)
        imageView = viewImage as ImageView
        val paramImage = LayoutParams(LayoutParams.MATCH_PARENT, 0)
        paramImage.dimensionRatio = "16:9"
        paramImage.startToStart = this.id
        paramImage.topToTop = this.id
        paramImage.endToEnd = this.id
        imageView.layoutParams = paramImage

        val viewBottom = inflater.inflate(R.layout.layout_voucher_bottom, null, false)
        txtTitle = viewBottom.findViewById(R.id.txt_voucher_title)
        txtSubtitle = viewBottom.findViewById(R.id.txt_voucher_subtitle)
        cardBottom = viewBottom as LinearLayout
        val paramBottom = LayoutParams(0, LayoutParams.WRAP_CONTENT)
        paramBottom.startToStart = imageView.id
        paramBottom.topToBottom = imageView.id
        paramBottom.endToEnd = imageView.id
        cardBottom.layoutParams = paramBottom

        addView(viewBottom)
        addView(imageView)
        imageView.visibility = View.INVISIBLE

        val theme = context.theme
        if (theme != null) {
            val typedArray = theme.obtainStyledAttributes(
                attrs,
                R.styleable.XVoucherView, 0, 0
            )
            try {
                srcRef =
                    typedArray.getResourceId(R.styleable.XVoucherView_android_src, 0)
                title =
                    typedArray.getString(R.styleable.XVoucherView_android_title).toString()
                subtitle =
                    typedArray.getString(R.styleable.XVoucherView_android_subtitle).toString()
                if (srcRef != 0) {
                    imageView.setImageResource(srcRef)
                } else {
                    imageView.setImageResource(R.mipmap.img_empty)
                }
                if (title == null || title.equals("null"))
                    title = "Default Title"
                if (subtitle == null || subtitle.equals("null"))
                    subtitle = "Default Subttile"
                txtTitle.text = title
                txtSubtitle.text = subtitle
            } finally {
                typedArray.recycle()
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (childCount > 0) {
            val bitmap: Bitmap? = if (imageView.drawable is BitmapDrawable) {
                (imageView.drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
            } else {
                val d: Drawable = imageView.drawable
                d.draw(canvas ?: return)
                Bitmap.createBitmap(
                    d.intrinsicWidth,
                    d.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                ).copy(Bitmap.Config.ARGB_8888, true)
            }
            val mLeft = imageView.left.toFloat()
            val mEnd = imageView.right.toFloat()
            drawCardTop(canvas)
            getCroppedBitmap(bitmap ?: return, canvas)
            drawHoles(canvas, mLeft)
            drawHoles(canvas, mEnd)
            drawCardBottom(canvas)
        }
        super.onDraw(canvas)
    }

    private fun getCroppedBitmap(bitmap: Bitmap, canvas: Canvas?) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.parseColor("#FFFFFF")
        val mLeft = imageView.left.toFloat()
        val mTop = imageView.top.toFloat()
        val mEnd = mLeft + imageView.width
        val mBottom = mTop + imageView.height
        val rectF = RectF(
            mLeft, mTop, mEnd, mBottom
        )
        val path = Path()
        val arr = floatArrayOf(
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius,
            0f, 0f,
            0f, 0f
        )
        path.addRoundRect(rectF, arr, Path.Direction.CW)
        canvas?.drawPath(path, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        val dstRect = Rect(
            mLeft.toInt(), mTop.toInt(), mEnd.toInt(), mBottom.toInt()
        )
        canvas?.drawBitmap(bitmap, dstRect, dstRect, paint)
    }

    private fun drawHoles(canvas: Canvas?, posX: Float) {
        val eraser = Paint(Paint.ANTI_ALIAS_FLAG)
        eraser.color = -0x1
        eraser.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        val size = imageView.height / 2f
        val radius = size / 4.5f
        val posY = imageView.height / 2f
        canvas?.drawCircle(posX, posY, radius, eraser)
    }

    private fun drawCardTop(canvas: Canvas?) {
        val card = Paint(Paint.ANTI_ALIAS_FLAG)
        card.color = Color.parseColor("#FFFFFF")
        val mLeft = imageView.left.toFloat()
        val mTop = imageView.top.toFloat()
        val mEnd = mLeft + imageView.width
        val mBottom = mTop + imageView.height
        val shadowRadius = 4.dp
        card.setShadowLayer(
            shadowRadius.toFloat(), 0f, 0f,
            Color.parseColor("#0D0A0A0A")
        )
        val rectF = RectF(
            mLeft, mTop, mEnd, mBottom
        )
        val path = Path()
        val arr = floatArrayOf(
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius,
            0f, 0f,
            0f, 0f
        )
        path.addRoundRect(rectF, arr, Path.Direction.CW)
        canvas?.drawPath(path, card)
    }

    private fun drawCardBottom(canvas: Canvas?) {
        val card = Paint(Paint.ANTI_ALIAS_FLAG)
        card.color = Color.parseColor("#FFFFFF")
        val mLeft = imageView.left.toFloat()
        val mTop = imageView.bottom.toFloat()
        val mEnd = imageView.right.toFloat()
        val mBottom = cardBottom.bottom.toFloat()
        val shadowRadius = 3.dp
        card.setShadowLayer(
            shadowRadius.toFloat(), 0f, 3f,
            Color.parseColor("#0D0A0A0A")
        )
        val rectF = RectF(
            mLeft, mTop, mEnd, mBottom
        )
        val path = Path()
        val arr = floatArrayOf(
            0f, 0f,
            0f, 0f,
            cornerRadius, cornerRadius,
            cornerRadius, cornerRadius
        )
        path.addRoundRect(rectF, arr, Path.Direction.CW)
        canvas?.drawPath(path, card)
    }

    /**
     * Integer to Density Pixel
     */
    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}