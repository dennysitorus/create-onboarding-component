package com.example.create_onboarding_component

import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    // for onboarding adapter
    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter

    // for inactive indicator
    private lateinit var indicatorsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems() {
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onBoardingImage = R.drawable.telecomunicating_1,
                    title = "Manage Easily",
                    description = "Organize all of your item on a single dashboard, as simple as a click!"
                ),
                OnboardingItem(
                    onBoardingImage = R.drawable.telecomunicating_2,
                    title = "Guide Your Customer",
                    description = "Cut your expense for a tutor."
                ),
                OnboardingItem(
                    onBoardingImage = R.drawable.telecomunicating_3,
                    title = "Worldwide Notification",
                    description = "Enjoy a simple yet cheap notification for customer world-wide."
                ),
            )
        )

        val onBoardingViewPager = findViewById<ViewPager2>(R.id.onboarding_view_pager)
        onBoardingViewPager.adapter = onboardingItemsAdapter
        onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        // navigate to an activity
        findViewById<ImageView>(R.id.imageNext).setOnClickListener {
            if (onBoardingViewPager.currentItem + 1 < onboardingItemsAdapter.itemCount) {
                onBoardingViewPager.currentItem += 1
            } else {
                navigateToHomeActivity()
            }
        }

        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            navigateToHomeActivity()
        }

        findViewById<MaterialButton>(R.id.btnGetStarted).setOnClickListener {
            navigateToHomeActivity()
        }
    }

    private fun navigateToHomeActivity() {
//        startActivity(Intent(applicationContext, MainActivity::class.java))
//        finish()
        Toast.makeText(this, "Navigated to next page.", Toast.LENGTH_SHORT).show()
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCOunt = indicatorsContainer.childCount
        for (i in 0 until childCOunt) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}