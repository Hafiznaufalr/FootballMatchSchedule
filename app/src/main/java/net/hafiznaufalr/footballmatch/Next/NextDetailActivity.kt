package net.hafiznaufalr.footballmatch.Next

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import net.hafiznaufalr.footballmatch.Model.Event
import net.hafiznaufalr.footballmatch.Model.Team
import net.hafiznaufalr.footballmatch.R
import net.hafiznaufalr.footballmatch.api.ApiReq
import net.hafiznaufalr.footballmatch.utils.invisible
import net.hafiznaufalr.footballmatch.utils.visible
import org.jetbrains.anko.*

class NextDetailActivity : AppCompatActivity(), NextInterface {
    private var resEventList: MutableList<Event> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var nextPresenter: NextDetailPresenter
    private lateinit var resEvent: Event
    private lateinit var eventId: String
    private lateinit var strHomeName: String
    private lateinit var strAwayName: String
    private lateinit var homeTeamObj: Team
    private lateinit var awayTeamObj: Team
    private lateinit var homeBadgeUrl: String
    private lateinit var awayBadgeUrl: String
    private lateinit var matchDate: TextView
    private lateinit var matchTime: TextView
    private lateinit var matchTitle: TextView
    private lateinit var homeName: TextView
    private lateinit var homeBadges: ImageView
    private lateinit var awayName: TextView
    private lateinit var awayBadges: ImageView

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun setEvent(list: List<Event>) {
        resEvent = list[0]
        setView()
    }

    override fun setHomeBadges(teams: List<Team>) {
        homeTeamObj = teams[0]
        homeBadgeUrl = homeTeamObj.strTeamBadge
        Picasso.get().load(homeBadgeUrl).into(homeBadges)
    }

    override fun setAwayBadges(teams: List<Team>) {
        awayTeamObj = teams[0]
        awayBadgeUrl = awayTeamObj.strTeamBadge
        Picasso.get().load(awayBadgeUrl).into(awayBadges)
    }

    fun setView() {
        matchDate.text = resEvent.dateEvent
        matchTime.text = resEvent.strTime
        matchTitle.text = resEvent.strEvent
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventId = intent.getStringExtra("MATCH_ID")
        strHomeName = intent.getStringExtra("HOME_NAME")
        strAwayName = intent.getStringExtra("AWAY_NAME")

        // THE BIG LAYOUT
        relativeLayout {
            lparams(width = matchParent, height = wrapContent)
            // Scroll view for detail
            scrollView {
                relativeLayout{
                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                    linearLayout {
                        orientation = LinearLayout.VERTICAL

                        // Match Date
                        matchDate = textView {
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            height = wrapContent
                            width = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Home and Away team name
                        matchTitle = textView {
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            height = wrapContent
                            width = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }
                        // home away badge
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            // Home badge
                            homeBadges = imageView {
                            }.lparams {
                                width = dip(80)
                                height = dip(80)
                                weight = 3f
                            }
                            // score
                            textView {
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                                textSize = 28f
                                setTypeface(null, Typeface.BOLD)
                                text = "vs"
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                weight = 1f
                                gravity = Gravity.CENTER_VERTICAL
                            }
                            // Away badge
                            awayBadges = imageView {
                            }.lparams {
                                width = dip(80)
                                height = dip(80)
                                weight = 3f
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            topMargin = dip(15)
                            bottomMargin = dip(15)
                        }

                        // Start time header text
                        textView {
                            text = "Will start at"
                            setTypeface(null, Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        //
                        matchTime = textView {
                            setTypeface(null, Typeface.BOLD)
                            textSize = 30f
                        }.lparams {
                            height = wrapContent
                            width = wrapContent
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                    }.lparams(width = matchParent, height = wrapContent)
                }.lparams(width = matchParent, height = wrapContent)
            }

        }
        init()
    }

    fun init() {
        val request = ApiReq()
        val gson = Gson()
        nextPresenter = NextDetailPresenter(this, request, gson)
        nextPresenter.getEventList(eventId)
        nextPresenter.getBadges("home",strHomeName)
        nextPresenter.getBadges("away",strAwayName)
    }
}

