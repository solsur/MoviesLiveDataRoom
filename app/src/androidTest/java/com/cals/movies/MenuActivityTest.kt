@file:Suppress("DEPRECATION")

package com.cals.movies

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cals.movies.ui.menu.MenuActivity
import com.cals.movies.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class MenuActivityTest{

    private val staticMovie = DataStatic.generateStaticMovie()
    private val staticTvShow = DataStatic.generateStaticTvShow()

    @Before
    fun setup(){
        ActivityScenario.launch(MenuActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(staticMovie.size))
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.active_favorite)).perform(click())
        onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_title)).check(matches(withText(staticMovie[0].title)))
        onView(withId(R.id.tv_item_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_release)).check(matches(withText(staticMovie[0].release_date)))
        onView(withId(R.id.tv_item_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_overview)).check(matches(withText(staticMovie[0].description)))
        onView(withId(R.id.img_detail)).check(matches(isDisplayed()))
        Espresso.pressBack()
    }

    @Test
    fun loadtvshow(){
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(staticMovie.size))
    }

    @Test
    fun loadDetailtvshow(){
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_show)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.active_favorite)).perform(click())
        onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_title)).check(matches(withText(staticTvShow[0].title)))
        onView(withId(R.id.tv_item_release)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_release)).check(matches(withText(staticTvShow[0].release_date)))
        onView(withId(R.id.tv_item_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_overview)).check(matches(withText(staticTvShow[0].description)))
        onView(withId(R.id.img_detail)).check(matches(isDisplayed()))

    }

    @Test
    fun loadFavorite(){
        onView(withId(R.id.navigation_notifications)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(staticMovie.size))
    }

}