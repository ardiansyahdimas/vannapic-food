package com.vanapic.food.ui.detail

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vanapic.food.R
import com.vanapic.food.core.domain.model.Food
import com.vanapic.food.core.ui.FoodViewModelFactory
import com.vanapic.food.databinding.ActivityDetailFoodBinding
import retrofit2.Call
import retrofit2.Response

class DetailFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailFoodBinding
    private lateinit var detailFoodViewModel: DetailFoodViewModel
    private lateinit var ivAvatar: ImageView
    private lateinit var ivImgFood: ImageView
    private var EXPAND_AVATAR_SIZE: Float = 0F
    private var COLLAPSE_IMAGE_SIZE: Float = 0F
    private var margin: Float = 0F
    private lateinit var toolbar: Toolbar
    private lateinit var appBarLayout: AppBarLayout
    private var cashCollapseState: Pair<Int, Int>? = null
    private lateinit var cardNamePosition: CardView
    private lateinit var foodName: TextView
    private lateinit var foodPrice: TextView
    private lateinit var titleToolbarTextSingle: TextView
    private lateinit var collapsingAvatarContainer: FrameLayout
    private lateinit var background: FrameLayout
    private var _updateFav = MutableLiveData<Boolean>()
    private val updateFav: LiveData<Boolean> = _updateFav
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        EXPAND_AVATAR_SIZE = resources.getDimension(R.dimen.default_expanded_image_size)
        COLLAPSE_IMAGE_SIZE = resources.getDimension(R.dimen.default_collapsed_image_size)
        margin = resources.getDimension(R.dimen.avatar_margin)
        collapsingAvatarContainer = binding.stuffContainer
        appBarLayout = binding.appBarLayout
        toolbar = binding.animToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        ivAvatar = binding.imgbAvatarWrap
        ivImgFood = binding.foodImg
        cardNamePosition = binding.cardNameAndPosition
        foodName = binding.tvName
        foodPrice = binding.tvPrice
        titleToolbarTextSingle = binding.tvProfileNameSingle
        background = binding.flBackground
//        favBtn = binding.fab


        val factory = FoodViewModelFactory.getInstance(this)
        detailFoodViewModel = ViewModelProvider(this, factory)[DetailFoodViewModel::class.java]
        val detailFood = intent.getParcelableExtra<Food>(EXTRA_DATA)
        showDetailFood(detailFood)

        /**/
        appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
                if (isCalculated.not()) {
                    startAvatarAnimatePointY = Math.abs((appBarLayout.height - EXPAND_AVATAR_SIZE - toolbar.height / 2) / appBarLayout.totalScrollRange)
                    animateWeigt = 1 / (1 - startAvatarAnimatePointY)
                    isCalculated = true
                }

                val offset = Math.abs(i / appBarLayout.totalScrollRange.toFloat())
                updateViews(offset)
            })

    }

    private var startAvatarAnimatePointY: Float = 0F
    private var animateWeigt: Float = 0F
    private var isCalculated = false

    private fun updateViews(percentOffset: Float) {

        /*Collapsed/expended sizes for views*/
        val result: Pair<Int, Int> = when {
            percentOffset < ABROAD -> {
                Pair(TO_EXPANDED_STATE, cashCollapseState?.second
                    ?: WAIT_FOR_SWITCH)
            }
            else -> {
                Pair(TO_COLLAPSED_STATE, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
            }
        }
        result.apply {
            var translationY = 0f
            var headContainerHeight = 0f
            val translationX: Float
            var currentImageSize = 0
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED_STATE -> {
                            translationY = toolbar.height.toFloat()
                            headContainerHeight = appBarLayout.totalScrollRange.toFloat()
                            currentImageSize = EXPAND_AVATAR_SIZE.toInt()
                            /**/
                            cardNamePosition.visibility = View.VISIBLE
                            titleToolbarTextSingle.visibility = View.INVISIBLE
                            background.setBackgroundColor(ContextCompat.getColor(this@DetailFoodActivity, R.color.color_transparent))
                            /**/
                            ivAvatar.translationX = 0f
                            ivAvatar.visibility = View.INVISIBLE
                        }

                        TO_COLLAPSED_STATE -> {
                            background.setBackgroundColor(ContextCompat.getColor(this@DetailFoodActivity, R.color.white))
                            currentImageSize = COLLAPSE_IMAGE_SIZE.toInt()
                            translationY = appBarLayout.totalScrollRange.toFloat() - (toolbar.height - COLLAPSE_IMAGE_SIZE) / 2
                            headContainerHeight = toolbar.height.toFloat()
                            translationX = appBarLayout.width / 2f - COLLAPSE_IMAGE_SIZE / 2 - margin * 2
                            /**/
                            ValueAnimator.ofFloat(ivAvatar.translationX, translationX).apply {
                                addUpdateListener {
                                    if (cashCollapseState!!.first == TO_COLLAPSED_STATE) {
                                        ivAvatar.translationX = it.animatedValue as Float
                                    }
                                }
                                interpolator = AnticipateOvershootInterpolator()
                                startDelay = 69
                                duration = 350
                                start()
                            }
                            /**/
                            cardNamePosition.visibility = View.INVISIBLE
                            titleToolbarTextSingle.apply {
                                visibility = View.VISIBLE
                                alpha = 0.2f
                                this.translationX = width.toFloat() / 2
                                animate().translationX(0f)
                                    .setInterpolator(AnticipateOvershootInterpolator())
                                    .alpha(1.0f)
                                    .setStartDelay(69)
                                    .setDuration(450)
                                    .setListener(null)
                            }
                            ivAvatar.visibility = View.VISIBLE
                        }
                    }

                    ivAvatar.apply {
                        layoutParams.height = currentImageSize
                        layoutParams.width = currentImageSize
                    }
                    collapsingAvatarContainer.apply {
                        layoutParams.height = headContainerHeight.toInt()
                        this.translationY = translationY
                        requestLayout()
                    }
                    /**/
                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    /* Collapse avatar img*/
                    ivAvatar.apply {
                        if (percentOffset > startAvatarAnimatePointY) {
                            val animateOffset = (percentOffset - startAvatarAnimatePointY) * animateWeigt
                            val avatarSize = EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * animateOffset

                            this.layoutParams.also {
                                if (it.height != Math.round(avatarSize)) {
                                    it.height = Math.round(avatarSize)
                                    it.width = Math.round(avatarSize)
                                    this.requestLayout()
                                }
                            }

                            this.translationY = (COLLAPSE_IMAGE_SIZE - avatarSize) * animateOffset

                        } else {
                            this.layoutParams.also {
                                if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                                    it.height = EXPAND_AVATAR_SIZE.toInt()
                                    it.width = EXPAND_AVATAR_SIZE.toInt()
                                    this.layoutParams = it
                                }
                            }
                        }
                    }
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        const val ABROAD = 0.99f
        const val TO_EXPANDED_STATE = 0
        const val TO_COLLAPSED_STATE = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
        const val EXTRA_DATA = "extra_data"
    }

    private fun showDetailFood(detailFood: Food?) {
        detailFood?.let {
            titleToolbarTextSingle.text = detailFood.name
            foodName.text = detailFood.name
            foodPrice.text = "Stok: ${detailFood.stock} pcs | Harga: Rp. ${detailFood.price}/pcs"
            binding.tvDescription.text = detailFood.description
            Glide.with(this@DetailFoodActivity)
                .load(detailFood.photo)
                .into(ivAvatar)

            Glide.with(this@DetailFoodActivity)
                .load(detailFood.photo)
                .into(ivImgFood)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        val menuBlock = menu.findItem(R.id.fab_favorite)
        val detailFood = intent.getParcelableExtra<Food>(EXTRA_DATA)
        val currentStatusFav = detailFood?.isFavorite
        if (currentStatusFav == true) menuBlock.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_favorite )) else   menuBlock.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_favorite_border ))
        updateFav.observe(this, {isFav ->
            if(isFav) menuBlock.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_favorite )) else   menuBlock.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_favorite_border ))
        })
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SetTextI18n", "NullSafeMutableLiveData")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.fab_favorite -> {
                    val detailFood = intent.getParcelableExtra<Food>(EXTRA_DATA)
                    var statusFav = detailFood?.isFavorite
                    if (detailFood != null) {
                        statusFav = !statusFav!!
                        detailFoodViewModel.setFavoriteFood(detailFood, statusFav)
                        _updateFav.postValue(statusFav)
                    }
                }
            }
        return super.onOptionsItemSelected(item)
    }
}